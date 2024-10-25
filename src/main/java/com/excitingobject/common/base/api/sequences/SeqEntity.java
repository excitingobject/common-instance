package com.excitingobject.common.base.api.sequences;

import com.excitingobject.common.base.EoConstants;
import com.excitingobject.common.base.api.EoEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = EoConstants.TB_SEQ)
@Data
public class SeqEntity extends EoEntity {

    @Id
    @Column(name = K_TABLE_NAME)
    public String tableName;

    @Column(name = K_SEQ, precision = 10, scale = 0)
    private BigDecimal seq;

    @Column(name = K_PREFIX, length = 6)
    private String prefix;

    public SeqEntity() {
        super();
    }

    public SeqEntity(String tableName, String prefix) {
        super();
        this.tableName = tableName;
        this.seq = new BigDecimal("-1");
        this.prefix = prefix;
    }

    @Override
    protected void initDefaultDto(Map dto) {
        dto.put(K_TABLE_NAME, tableName);
        dto.put(K_SEQ, seq);
        dto.put(K_PREFIX, prefix);
    }
}
