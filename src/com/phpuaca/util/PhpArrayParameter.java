package com.phpuaca.util;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.ArrayCreationExpression;
import com.jetbrains.php.lang.psi.elements.ParameterList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PhpArrayParameter extends PhpParameter {

    public PhpArrayParameter(@NotNull PsiElement parameter, int number) {
        super(parameter, number);
    }

    public List<String> getValues() {
        List<String> values = new ArrayList<String>();
        ArrayCreationExpression arrayCreationExpression = (ArrayCreationExpression) getParameter();
        for (PsiElement child : arrayCreationExpression.getChildren()) {
            values.add(StringUtil.unquoteString(child.getText()));
        }
        return values;
    }

    @Nullable
    public static PhpArrayParameter create(@NotNull ParameterList parameterList, int parameterNumber) {
        PhpArrayParameter phpArrayParameter = null;
        PhpParameter phpParameter = PhpParameter.create(parameterList, parameterNumber);
        if (phpParameter != null) {
            PsiElement parameter = phpParameter.getParameter();
            if (parameter instanceof ArrayCreationExpression) {
                phpArrayParameter = new PhpArrayParameter(parameter, parameterNumber);
            }
        }
        return phpArrayParameter;
    }
}
