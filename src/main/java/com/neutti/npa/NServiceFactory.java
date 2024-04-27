package com.neutti.npa;


import com.neutti.npa.service.korea.DataApiService;

import java.net.URL;

public class NServiceFactory<T> {
    public static <E extends NService> E getService(NHostType type) throws NpaException {
        switch (type){
            case DATA_GO:
                return (E) DataApiService.getInstance();
        }
        throw new NpaException("NHostType 을 지정해주세요.");
    }
}
