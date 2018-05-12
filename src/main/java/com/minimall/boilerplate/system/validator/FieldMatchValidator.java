package com.minimall.boilerplate.system.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import java.util.Objects;

import static org.springframework.beans.BeanUtils.getPropertyDescriptor;
import static org.springframework.util.ReflectionUtils.invokeMethod;

/**
 * Title: .
 * <p>Description: </p>
 *
 * @author huangtao
 */
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

  private String firstFieldName;
  private String secondFieldName;
  /**
   * Initializes the validator in preparation for
   * {@link #isValid(Object, ConstraintValidatorContext)} calls.
   * The constraint annotation for a given constraint declaration
   * is passed.
   * <p/>
   * This method is guaranteed to be called before any use of this instance for
   * validation.
   *
   * @param constraintAnnotation annotation instance for a given constraint declaration
   */
  @Override
  public void initialize(final FieldMatch constraintAnnotation) {
    firstFieldName = constraintAnnotation.first();
    secondFieldName = constraintAnnotation.second();
  }

  /**
   * Implements the validation logic.
   * The state of {@code value} must not be altered.
   * <p/>
   * This method can be accessed concurrently, thread-safety must be ensured
   * by the implementation.
   *
   * @param value   object to validate
   * @param context context in which the constraint is evaluated
   * @return {@code false} if {@code value} does not pass the constraint
   */
  @Override
  public boolean isValid(final Object value, final ConstraintValidatorContext context) {

    Method firstGetter  = getPropertyDescriptor(value.getClass(), firstFieldName)
        .getReadMethod();
    final Object firstValue = invokeMethod(firstGetter, value);

    Method secondGetter  = getPropertyDescriptor(value.getClass(), secondFieldName)
        .getReadMethod();
    final Object secondValue = invokeMethod(secondGetter, value);

    if(!Objects.equals(firstValue, secondValue)) {
      context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
          .addPropertyNode(secondFieldName)
          .addConstraintViolation();
      return false;
    }
    return true;
  }
}
