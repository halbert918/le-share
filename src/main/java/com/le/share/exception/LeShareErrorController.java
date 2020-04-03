package com.le.share.exception;

import com.le.share.config.EntityBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by yinbohe.
 * Date 2019/8/21
 * Description
 */
@RestController
public class LeShareErrorController extends BasicErrorController {

  private static final Logger logger = LoggerFactory.getLogger(LeShareErrorController.class);

  private static final String PATH = "/error";

  private static final String RESULT_STATUS = "status";

  private static final String RESULT_MESSAGE = "message";


  public LeShareErrorController() {
    super(new DefaultErrorAttributes(), new ErrorProperties());
  }

  /**
   * error页异常处理.
   *
   * @param request request
   * @return ResponseEntity
   */
  @RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
    Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
    HttpStatus status = getStatus(request);
    String code = String.valueOf(body.remove(RESULT_STATUS));
    String message = String.valueOf(body.remove(RESULT_MESSAGE));
    return new ResponseEntity(new EntityBody(code == null ? String.valueOf(status.value()) : code,
        message == null ? status.getReasonPhrase() : message, body), status);
  }

  @Override
  public String getErrorPath() {
    return PATH;
  }


}
