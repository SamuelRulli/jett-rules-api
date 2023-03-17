package com.cygnus.jett.commons;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class CommonsUtils {
    public final static Logger log = LoggerFactory.getLogger(CommonsUtils.class);

    public Integer result;
    public Map<String, Boolean> rulesMap = new HashMap<>();

    public Integer createRandomScore(final int min, final int max){
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public String currentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println(LocalDateTime.now().toString());
        return LocalDateTime.now().toString();
    }

    public Boolean isNotEmpty(final Collection collection){
        return CollectionUtils.isNotEmpty(collection);
    }

    public Boolean isEmpty(final Collection collection){
        return CollectionUtils.isEmpty(collection);
    }

    public Boolean isEmpty(final String value){
        return StringUtils.isEmpty(value);
    }

    public Boolean isNotEmpty(final String value){
        return StringUtils.isNotEmpty(value);
    }

    public Boolean itemEqualsString(List<?> listSource, String fieldSource,
                                    List<?> listTarget, String fieldTarget) {

        List<String> listStringSource = createList(listSource, fieldSource);
        List<String> listStringTarget = createList(listTarget, fieldTarget);

        if(CollectionUtils.isEmpty(listStringSource) || CollectionUtils.isEmpty(listStringTarget) ||
           CollectionUtils.isEmpty(listSource) || CollectionUtils.isEmpty(listTarget)){
            return false;
        }

        return listStringSource.stream()
                .anyMatch(listStringTarget::contains);
    }

    private List<String> createList(List<?> list, String field) {
        List<String> listString = new ArrayList<>();
        try {
            for (Object obj : list) {
                Method method = obj.getClass().getMethod(field);
                listString.add(method.invoke(obj).toString());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listString;
    }
}
