package com.excitingobject.common.api.response;

import com.excitingobject.common.EoConstants;
import com.excitingobject.common.api.EoEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class EoResponseDto {
    private String code = EoResponseStatus.SUCCESS.getCode();

    enum DataType {
        NULL,
        OBJECT,
        LIST
    }
    private DataType dataType = DataType.NULL;
    private Object data = null;

    private Map<String, Object> pageInfo = null;

    private String message = "";

    public EoResponseDto(Object data, String code, String message) {
        setData(data);
        this.code = code;
        this.message = message;
    }

    private Object refineData (Object data) {
        try {
            if (data instanceof EoEntity) {
                return ((EoEntity) data).getResponseDto();
            } else if (data instanceof Map) {
                return data;
            } else if (data instanceof Iterable) {
                List<Object> list = new ArrayList<>();
                for (Object d: (Iterable)data) {
                    list.add(refineData(d));
                }
                return list;
            } else {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.convertValue(data, Map.class);
            }
        } catch (Exception e) {
            return null;
        }
    }

    private void setData(Object data) {
        this.data = this.refineData(data);
        if(this.data == null) {
            this.dataType = DataType.NULL;
        } else if(this.data instanceof Map) {
            this.dataType = DataType.OBJECT;
        } else if(this.data instanceof List) {
            this.dataType = DataType.LIST;
            if(data instanceof Page) {
                this.setPageInfo((Page) data);
            }
        }
    }

    private void setPageInfo(Page page) {
        this.pageInfo = new HashMap<>();
        this.pageInfo.put(EoConstants.K_NUMBER, page.getNumber());
        this.pageInfo.put(EoConstants.K_SIZE, page.getSize());
        this.pageInfo.put(EoConstants.K_TOTAL_ELEMENTS, page.getTotalElements());
        this.pageInfo.put(EoConstants.K_TOTAL_PAGES, page.getTotalPages());
        this.pageInfo.put(EoConstants.K_IS_FIRST, page.isFirst());
        this.pageInfo.put(EoConstants.K_IS_LAST, page.isLast());
    }
}
