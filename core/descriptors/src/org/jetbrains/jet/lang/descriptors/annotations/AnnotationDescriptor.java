package org.jetbrains.jet.lang.descriptors.annotations;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jet.lang.descriptors.ValueParameterDescriptor;
import org.jetbrains.jet.lang.resolve.constants.CompileTimeConstant;
import org.jetbrains.jet.lang.types.JetType;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface AnnotationDescriptor {
    List<AnnotationDescriptor> EMPTY_LIST = Collections.emptyList();

    @NotNull
    JetType getType();

    @Nullable
    CompileTimeConstant<?> getValueArgument(@NotNull ValueParameterDescriptor valueParameterDescriptor);

    @NotNull
    Map<ValueParameterDescriptor, CompileTimeConstant<?>> getAllValueArguments();
}
