package com.excitingobject.common.api.sequences;

import com.excitingobject.common.EoConstants;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.ParameterMode;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.ProcedureOutputs;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class SeqGenerator implements IdentifierGenerator, Configurable, EoConstants {

    @Autowired
    private SeqRepository seqRepository;

    protected abstract void initSeqData(List<SeqEntity> list);

    @PostConstruct
    private void init() {
        List<SeqEntity> list = new ArrayList<>();
        this.initSeqData(list);
        for (SeqEntity e : list) {
            if (e != null && !seqRepository.existsById(e.getTableName())) {
                seqRepository.save(e);
            }
        }
    }

    static final String KEY_IN = "IN_TABLE_NAME";
    static final String KEY_OUT_SEQ = "OUT_SEQ";
    static final String KEY_OUT_PREFIX = "OUT_PREFIX";
    static final String PROCEDURE = "SEQ_GENERATOR";
    static final String FORMAT_DATE_CODE = "yyyyMMdd";

    private final SimpleDateFormat dataCodeFormat = new SimpleDateFormat(FORMAT_DATE_CODE);

    private String tableName;

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        this.tableName = ConfigurationHelper.getString(K_TABLE_NAME, params);
//        logger.info("IdGenerator tableName : " + tableName);
    }

    /**
     * ID 생성 : prefix + date(8자리) + seq(6자리)
     * {prefix}{dateCode: YYYYMMDD}{seqCode: 36진수 6자리 문자열(000000~ZZZZZZ)}
     */
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        try {
            ProcedureCall procedureCall = session.createStoredProcedureCall(PROCEDURE); // 프로시저 호출 객체 생성
            procedureCall.registerParameter(KEY_IN, String.class, ParameterMode.IN); // 프로시저 파라메터 등록 (입력 변수: IN_TABLE_NAME)
            procedureCall.registerParameter(KEY_OUT_SEQ, Long.class, ParameterMode.OUT); // 프로시저 파라메터 등록 (출력 변수: OUT_SEQ)
            procedureCall.registerParameter(KEY_OUT_PREFIX, String.class, ParameterMode.OUT); // 프로시저 파라메터 등록 (출력 변수: OUT_PREFIX)
            procedureCall.setParameter(KEY_IN, tableName); // 프로시저의 입력 변수 값 세팅
            ProcedureOutputs outputs = procedureCall.getOutputs(); // 프로시저 호출

            // ID : {prefix}{dateCode}{seqCode}
            Long seq = (Long) outputs.getOutputParameterValue(KEY_OUT_SEQ);
            String prefix = (String) outputs.getOutputParameterValue(KEY_OUT_PREFIX);
            String dateCode = dataCodeFormat.format(new Date()); // 8자리 문자열(yyyyMMdd)
            String seqCode = getSeqCode(new BigDecimal(seq.toString())); // 6자리 문자열(000000 ~ ZZZZZZ)

//            logger.info("[IdGenerator - tableName:"+tableName+"] Create ID : " + id);
            return prefix + dateCode + seqCode;
        } catch (Exception e) {
//            logger.error(e.getMessage());
            throw new HibernateException(e);
        }
    }

    /**
     * seq 값을 seqCode 로 변환
     * - seq: 프로시저 반환 값 (10진수: 0 ~ 2,176,782,335)
     * - seqCode: 6자리 문자열 (36진수: 000000 ~ ZZZZZZ)
     */
    private String getSeqCode(BigDecimal seq) {
        BigDecimal num = seq; // 10진수값
        BigDecimal su = new BigDecimal("36"); // 변환 진수(36)
        int digit = 6; // 변환 진수 자릿수

        Stack<String> stack = new Stack<>();
        while (num.compareTo(BigDecimal.ZERO) > 0) {
            // cNum(10진수값을 변환 진수(36)로 나눈 나머지 : num & su)
            BigDecimal cNum = num.remainder(su);
            if (cNum.compareTo(new BigDecimal("9")) > 0) {
                // 나머지가 10 이상이면 아스키코드로 치환 A(65)~Z(90)
                int ASC_A = 'A';
                int _c = ASC_A + (cNum.intValue() - 10);
                char c = (char) _c;
                stack.push(Character.toString(c));
            } else {
                stack.push(cNum.toString());
            }
            // 몫(나머지 버림)으로 재귀
            num = num.divide(su, 0, RoundingMode.DOWN);
        }
        // 36진수 문자열 조합
        StringBuilder seqCode = new StringBuilder();
        while (!stack.isEmpty()) {
            String val = stack.pop();
            seqCode.append(val);
        }
        // padding (앞에 '0'채우기)
        return prefixPadding(seqCode.toString(), '0', digit);
    }

    public String prefixPadding(String str, char pad, int digit) {
        StringBuilder code = new StringBuilder(str);
        for (int i = (code.toString().getBytes()).length; i < digit; i++) {
            code.insert(0, pad);
        }
        return code.toString();
    }
}
