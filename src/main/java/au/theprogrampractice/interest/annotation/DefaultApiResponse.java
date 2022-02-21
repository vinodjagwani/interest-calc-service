package au.theprogrampractice.interest.annotation;

import au.theprogrampractice.interest.constant.SwaggerConstants;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@ApiResponses(value = {
        @ApiResponse(code = 200, message = SwaggerConstants.SUCCESS_PHRASE),
        @ApiResponse(code = 400, message = SwaggerConstants.INVALID_REQUEST),
        @ApiResponse(code = 400, message = SwaggerConstants.UN_PROCESSABLE_REQUEST_PHRASE),
        @ApiResponse(code = 401, message = SwaggerConstants.UNAUTHORIZED_REQUEST_PHRASE),
        @ApiResponse(code = 403, message = SwaggerConstants.FORBIDDEN_REQUEST_PHRASE),
        @ApiResponse(code = 502, message = SwaggerConstants.GATEWAY_TIMEOUT_REQUEST_PHRASE),
})
public @interface DefaultApiResponse {
}
