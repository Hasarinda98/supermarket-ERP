package com.bci.productcrud.exception;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.*;
@RestControllerAdvice
public class GlobalExceptionHandler {
 @ExceptionHandler({ProductNotFoundException.class,SupplierNotFoundException.class})
 ResponseEntity<Object> notFound(RuntimeException ex){return body(HttpStatus.NOT_FOUND,ex.getMessage(),null);}
 @ExceptionHandler({DuplicateBarcodeException.class,DuplicateSupplierEmailException.class,SupplierInUseException.class})
 ResponseEntity<Object> conflict(RuntimeException ex){return body(HttpStatus.CONFLICT,ex.getMessage(),null);}
 @ExceptionHandler(MethodArgumentNotValidException.class)
 ResponseEntity<Object> invalid(MethodArgumentNotValidException ex){Map<String,String> e=new LinkedHashMap<>();ex.getBindingResult().getFieldErrors().forEach(x->e.put(x.getField(),x.getDefaultMessage()));return body(HttpStatus.BAD_REQUEST,"Validation failed",e);}
 @ExceptionHandler(Exception.class)
 ResponseEntity<Object> other(Exception ex){return body(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage(),null);}
 private ResponseEntity<Object> body(HttpStatus s,String m,Object errors){Map<String,Object>b=new LinkedHashMap<>();b.put("timestamp",Instant.now());b.put("status",s.value());b.put("message",m);if(errors!=null)b.put("errors",errors);return ResponseEntity.status(s).body(b);}
}
