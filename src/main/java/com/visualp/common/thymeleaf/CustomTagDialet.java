package com.visualp.common.thymeleaf;

import com.visualp.common.util.TagUtils;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;
import java.util.Collections;
import java.util.Set;

/**
 * 1. 프로젝트명 : vboard
 * 2. 패키지명   : com.visualp.common.thymeleaf
 * 3. 작성일     : 2020. 06. 02. 오후 12:16
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */

@Component
public class CustomTagDialet extends AbstractDialect implements IExpressionObjectDialect {

    protected CustomTagDialet() {
        super("u");
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new IExpressionObjectFactory() {

            @Override
            public Set<String> getAllExpressionObjectNames() {
                return Collections.singleton("u");
            }

            @Override
            public Object buildObject(IExpressionContext context, String expressionObjectName) {
                return new TagUtils();
            }

            @Override
            public boolean isCacheable(String expressionObjectName) {
                return true;
            }
        };
    }
}
