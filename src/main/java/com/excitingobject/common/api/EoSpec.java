package com.excitingobject.common.api;

import com.excitingobject.common.EoConstants;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class EoSpec implements EoConstants {

    public static <Entity> Specification<Entity> isNull(String key) {
        return (root, query, builder) -> builder.isNull(root.get(key));
    }

    public static <Entity> Specification<Entity> isNotNull(String key) {
        return (root, query, builder) -> builder.isNotNull(root.get(key));
    }

    public static <Entity> Specification<Entity> like(String key, String data) {
        if (data != null) {
            return ((root, query, builder) -> builder.like(root.get(key), "%" + data + "%"));
        }
        return null;
    }

    public static <Entity> Specification<Entity> equal(String key, Object data) {
        if (data != null) {
            return ((root, query, builder) -> builder.equal(root.get(key), data));
        }
        return null;
    }

    public static <Entity> Specification<Entity> likeOr(String key, List<String> vins) {
        Specification<Entity> result = null;
        if(vins != null && vins.size() > 0 ) {
            for(String vin : vins) {
                if(vin != null) {
                    if(result == null) {
                        result = like(key, vin);
                    } else{
                        result = result.or(like(key, vin));
                    }
                }
            }
        }
        return result;
    }
    public static <Entity> Specification<Entity> notEqual(String key, Object data) {
        if (data != null) {
            return ((root, query, builder) -> builder.notEqual(root.get(key), data));
        }
        return null;
    }

    public static <Entity> Specification<Entity> betweenTime(String key, Date start, Date end) {
        if (start == null || end == null)
            return null;
        return ((root, query, builder) -> {
            try {
                return builder.between(root.get(key), start, end);
            } catch (Exception Entity) {
            }
            return null;
        });
    }

    public static <Entity> Specification<Entity> betweenDate(String key, Date start, Date end) {
        if (key == null || start == null || end == null)
            return null;
        return ((root, query, builder) -> {
            try {
                return builder.between(root.get(key), start, end);
            } catch (Exception Entity) {
            }
            return null;
        });
    }

    public static <Entity> Specification<Entity> equalDate(String key, Date date) {
        if (key == null || date == null)
            return null;
        return ((root, query, builder) -> {
            try {
                return builder.equal(root.get(key), date);
            } catch (Exception Entity) {
            }
            return null;
        });
    }

    public static <Entity> Specification<Entity> greaterDate(String key, Date date) {
        if (key == null || date == null)
            return null;
        return ((root, query, builder) -> {
            try {
                return builder.greaterThan(root.get(key), date);
            } catch (Exception Entity) {
            }
            return null;
        });
    }

    public static <Entity> Specification<Entity> greater(String key, String val) {
        if (key == null || val == null)
            return null;
        return ((root, query, builder) -> {
            try {
                return builder.greaterThan(root.get(key), val);
            } catch (Exception Entity) {
            }
            return null;
        });
    }
    public static <Entity> Specification<Entity> greater(String key, int val) {
        if (key == null)
            return null;
        return ((root, query, builder) -> {
            try {
                return builder.greaterThan(root.get(key), val);
            } catch (Exception Entity) {
            }
            return null;
        });
    }

    public static <Entity> Specification<Entity> greaterOrEqual(String key, String val) {
        if (key == null || val == null)
            return null;
        return ((root, query, builder) -> {
            try {
                return builder.greaterThanOrEqualTo(root.get(key), val);
            } catch (Exception Entity) {
            }
            return null;
        });
    }

    public static <Entity> Specification<Entity> lessDate(String key, Date date) {
        if (key == null || date == null)
            return null;
        return ((root, query, builder) -> {
            try {
                return builder.lessThan(root.get(key), date);
            } catch (Exception Entity) {
            }
            return null;
        });
    }

    public static <Entity> Specification<Entity> lessOrEqualDate(String key, Date date) {
        if (key == null || date == null)
            return null;
        return ((root, query, builder) -> {
            try {
                return builder.lessThanOrEqualTo(root.get(key), date);
            } catch (Exception Entity) {
            }
            return null;
        });
    }

    public static <Entity> Specification<Entity> greaterOrEqualDate(String key, Date date) {
        if (key == null || date == null)
            return null;
        return ((root, query, builder) -> {
            try {
                return builder.greaterThanOrEqualTo(root.get(key), date);
            } catch (Exception Entity) {
            }
            return null;
        });
    }

    public static <Entity> Specification<Entity> lessTime(String key, Timestamp time) {
        if (key == null || time == null)
            return null;
        return ((root, query, builder) -> {
            try {
                return builder.lessThan(root.get(key), time);
            } catch (Exception Entity) {
            }
            return null;
        });
    }

    public static <Entity> Specification<Entity> greaterTime(String key, Timestamp time) {
        if (key == null || time == null)
            return null;
        return ((root, query, builder) -> {
            try {
                return builder.greaterThan(root.get(key), time);
            } catch (Exception Entity) {
            }
            return null;
        });
    }

    public static <Entity> Specification<Entity> lessOrEqualTime(String key, Timestamp time) {
        if (key == null || time == null)
            return null;
        return ((root, query, builder) -> {
            try {
                return builder.lessThanOrEqualTo(root.get(key), time);
            } catch (Exception Entity) {
            }
            return null;
        });
    }

    public static <Entity> Specification<Entity> greaterOrEqualTime(String key, Timestamp time) {
        if (key == null || time == null)
            return null;
        return ((root, query, builder) -> {
            try {
                return builder.greaterThanOrEqualTo(root.get(key), time);
            } catch (Exception Entity) {
            }
            return null;
        });
    }

    public static <Entity> Specification<Entity> less(String key, String val) {
        if (key == null || val == null)
            return null;
        return ((root, query, builder) -> {
            try {
                return builder.lessThan(root.get(key), val);
            } catch (Exception Entity) {
            }
            return null;
        });
    }

    public static <Entity> Specification<Entity> lessOrEqual(String key, String val) {
        if (key == null || val == null)
            return null;
        return ((root, query, builder) -> {
            try {
                return builder.lessThanOrEqualTo(root.get(key), val);
            } catch (Exception Entity) {
            }
            return null;
        });
    }

    @SafeVarargs
    public static <Entity> Specification<Entity> joinAnd(Specification<Entity>... specs) {
        Specification<Entity> result = null;
        if (specs != null && specs.length > 0) {
            for (Specification<Entity> s : specs) {
                if (s != null) {
                    if (result == null) {
                        result = Specification.where(s);
                    } else {
                        result = result.and(s);
                    }
                }
            }
        }
        return result;
    }

    @SafeVarargs
    public static <Entity> Specification<Entity> joinOr(Specification<Entity>... specs) {
        Specification<Entity> result = null;

        if (specs != null && specs.length > 0) {
            for (Specification<Entity> s : specs) {
                if (s != null) {
                    if (result == null) {
                        result = Specification.where(s);
                    } else {
                        result = result.or(s);
                    }
                }
            }
        }
        return result;
    }

    public static String getToString(Map<String, Object> params, String key, String defaultVal) {
        try {
            Object val = params.get(key);
            if (val != null) {
                String str = val.toString();
                if (!"".equals(str.trim())) {
                    return str;
                }
            }
        } catch (Exception Entity) {
        }
        return defaultVal;
    }
}
