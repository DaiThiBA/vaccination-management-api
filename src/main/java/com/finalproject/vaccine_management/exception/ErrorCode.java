package com.finalproject.vaccine_management.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_ERROR(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1001, "User existed",HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1002,"Password must be atleast {min} character", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1003, "User not found", HttpStatus.NOT_FOUND ),
    INVALID_USERNAME(1004,"Username must be atleast {min} character", HttpStatus.BAD_REQUEST),
    INVALID_ERROR_CODE(9000, "Invalide enum key", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "Unauthorized", HttpStatus.FORBIDDEN),//theo chuẩn của http (nên hơi confuse)
    INVALID_DOB(1008, "Your age must be atleast {min}", HttpStatus.BAD_REQUEST),
    VACCINE_NOT_FOUND(1010, "Vaccine not found", HttpStatus.NOT_FOUND),
    BOOKING_NOT_FOUND(1020, "Booking not found", HttpStatus.NOT_FOUND),
    INVALID_STATUS_TRANSITION(1021, "Invalid status transition" , HttpStatus.BAD_REQUEST),
    SUPPLIER_NOT_FOUND(1021, "Supplier not found", HttpStatus.NOT_FOUND),
    SUPPLIER_EXISTED(1022, "Supplier existed",HttpStatus.BAD_REQUEST),
    SUPPLIER_CANNOT_DELETE(1023, "Supplier cannot be deleted because it is in use",HttpStatus.BAD_REQUEST),
    BATCH_NOT_FOUND(1030, "Batch not found",HttpStatus.NOT_FOUND ),
    SCHEDULE_NOT_FOUND(1040,"Schedule not found" , HttpStatus.NOT_FOUND),
    SLOT_FULL(1041,"Slot full" ,HttpStatus.BAD_REQUEST),
        ;

    private int code;
    private String message;
    private HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
