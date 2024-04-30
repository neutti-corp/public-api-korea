package com.neutti.npa;


import com.neutti.npa.service.korea.*;

import java.net.URL;

public class NServiceFactory<T> {
    public static <E extends NService> E getService(NHostType type) throws NpaException {
        switch (type){
            case DATA_GO:
                return (E) DataApiService.getInstance();
            case EXIM:
                return (E) EximApiService.getInstance();
            case SEX_OFFENDER:
                return (E) SexOffenderApiService.getInstance();
            case KOBIS:
                return (E) KobisApiService.getInstance();
            case MOLIT:
                return (E) MolitApiService.getInstance();
        }
        throw new NpaException("NHostType 을 지정해주세요.");
    }
}
