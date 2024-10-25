package com.excitingobject.common.base.api.sequences;

import com.excitingobject.common.base.EoConstants;
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

    protected abstract void initList(List<SeqEntity> list);

    @PostConstruct
    private void init() {
        List<SeqEntity> list = new ArrayList<>();
        this.initList(list);
        for (SeqEntity e: list) {
            if(e != null && !seqRepository.existsById(e.getTableName())) {
                seqRepository.save(e);
            }
        }
    }
    static final String KEY_IN = "IN_TABLE_NAME";
    static final String KEY_OUT_SEQ = "OUT_SEQ";
    static final String KEY_OUT_PREFIX = "OUT_PREFIX";
    static final String PROCEDURE = "SEQ_GENERATOR";
    static final String FORMAT_DATE_CODE = "yyyyMMdd";

    private SimpleDateFormat dataCodeFormat = new SimpleDateFormat(FORMAT_DATE_CODE);

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
            ProcedureCall procedureCall = session.createStoredProcedureCall(PROCEDURE); // 호출할 프로시저
            procedureCall.registerParameter(KEY_IN, String.class, ParameterMode.IN); // 프로시저에 선언된 입력받는 변수명 등록
            procedureCall.registerParameter(KEY_OUT_SEQ, Long.class, ParameterMode.OUT); // 프로시저에 선언된 반환하는 변수명 등록
            procedureCall.registerParameter(KEY_OUT_PREFIX, String.class, ParameterMode.OUT);
            procedureCall.setParameter(KEY_IN, tableName); // 프로시저의 변수에 전달할 파라미터 설정
            ProcedureOutputs outputs = procedureCall.getOutputs(); // 프로시저에서 반환하는값들

            // ID : {prefix}{dateCode: YYYYMMDD}{seqCode: 36진수 6자리 문자열(000000~ZZZZZZ)}
            Long seq = (Long) outputs.getOutputParameterValue(KEY_OUT_SEQ);
            String prefix = (String) outputs.getOutputParameterValue(KEY_OUT_PREFIX);
            String dateCode = dataCodeFormat.format(new Date());
            String seqCode = getSeqCode(new BigDecimal(seq.toString()));
            String id = prefix + dateCode + seqCode;

//            logger.info("[IdGenerator - tableName:"+tableName+"] Create ID : " + id);
            return id;
        } catch (Exception e) {
//            logger.error(e.getMessage());
            throw new HibernateException(e);
        }
    }

    /**
     * 10진수(seq)로 seqCode(36진수 6자리 문자열 : 000000 ~ ZZZZZZ)생성
     * @param seq
     * @return
     */
    private String getSeqCode(BigDecimal seq) {
        // seqCode : 36진수 6자리: 000000(0) ~ ZZZZZZ(2,176,782,335)
        BigDecimal num = seq; // 10진수값
        BigDecimal su = new BigDecimal("36"); // 변환 진수(36)
        int digit = 6; // 변환 진수 자릿수

        Stack<String> stack = new Stack<String>();
        while (num.compareTo(BigDecimal.ZERO) == 1) {
            // cNum(10진수값을 변환 진수(36)로 나눈 나머지 : num & su)
            BigDecimal cNum = num.remainder(su);
            if(cNum.compareTo(new BigDecimal("9"))==1) {
                // 나머지가 10 이상이면 아스키코드로 치환 A(65)~Z(90)
                int ASC_A = 'A';
                int _c = ASC_A + (cNum.intValue() - 10);
                Character c = Character.valueOf((char)_c);
                stack.push(c.toString());
            } else {
                stack.push(cNum.toString());
            }
            // 몫(나머지 버림)으로 재귀
            num = num.divide(su, 0, RoundingMode.DOWN);
        }
        // 36진수 문자열 조합
        String seqCode = "";
        while (!stack.isEmpty()) {
            String val = stack.pop();
            seqCode += val;
        }
        // padding (앞에 '0'채우기)
        return prefixPadding(seqCode, '0', digit);
    }

    public String prefixPadding(String str, char pad, int digit) {
        String code = str;
        for(int i = (code.getBytes()).length; i < digit; i++) {
            code = pad + code;
        }
        return code;
    }
}
