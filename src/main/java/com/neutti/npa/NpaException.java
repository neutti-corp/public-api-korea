package com.neutti.npa;

public class NpaException extends Exception {
    // 예외 클래스에 대한 기본 생성자
    public NpaException() {
        super();
    }

    // 메시지를 포함하는 생성자
    public NpaException(String message) {
        super(message);
    }

    // 메시지와 원인을 포함하는 생성자
    public NpaException(String message, Throwable cause) {
        super(message, cause);
    }

    // 원인만을 포함하는 생성자
    public NpaException(Throwable cause) {
        super(cause);
    }
}
