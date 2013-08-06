/*
 * Copyright 2010-2013 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.jet.plugin.caches;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.psi.impl.search.JavaSourceFilterScope;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.util.ArrayUtil;
import com.intellij.util.Function;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jet.lang.psi.JetDeclaration;
import org.jetbrains.jet.lang.psi.JetNamedFunction;
import org.jetbrains.jet.lang.psi.JetProperty;
import org.jetbrains.jet.plugin.libraries.JetSourceNavigationHelper;
import org.jetbrains.jet.plugin.stubindex.JetFunctionShortNameIndex;
import org.jetbrains.jet.plugin.stubindex.JetPropertyShortNameIndex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JetGotoSymbolContributor implements ChooseByNameContributor {
    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {
        Collection<String> items = StubIndex.getInstance().getAllKeys(JetFunctionShortNameIndex.getInstance().getKey(), project);
        items.addAll(StubIndex.getInstance().getAllKeys(JetPropertyShortNameIndex.getInstance().getKey(), project));

        return ArrayUtil.toStringArray(items);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        GlobalSearchScope baseScope = includeNonProjectItems ? GlobalSearchScope.allScope(project) : GlobalSearchScope.projectScope(project);
        GlobalSearchScope noLibrarySourcesScope = new JavaSourceFilterScope(baseScope);

        Collection<JetNamedFunction> functions = StubIndex.getInstance().get(
                JetFunctionShortNameIndex.getInstance().getKey(), name, project, noLibrarySourcesScope);

        Collection<JetProperty> properties = StubIndex.getInstance().get(
                JetPropertyShortNameIndex.getInstance().getKey(), name, project, noLibrarySourcesScope);

        //TODO: lazily
        List<JetDeclaration> items = new ArrayList<JetDeclaration>(Collections2.filter(functions, Predicates.notNull()));
        items.addAll(properties);
        List<NavigationItem> result = ContainerUtil.map(items, new Function<JetDeclaration, NavigationItem>() {
            @Override
            public NavigationItem fun(JetDeclaration item) {
                return JetSourceNavigationHelper.replaceBySourceDeclarationIfPresent(item);
            }
        });
        return ArrayUtil.toObjectArray(result, NavigationItem.class);
    }
}
