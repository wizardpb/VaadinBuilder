/*
 * Copyright (c) 2014 Prajna Inc
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.prajnainc.vaadinbuilder.factories

import com.prajnainc.vaadinbuilder.VaadinBuilderException
import com.vaadin.ui.AbstractOrderedLayout
import org.codehaus.groovy.runtime.typehandling.GroovyCastException

/**
 * Created by paul on 4/13/14.
 */
class OrderedLayoutFactory extends LayoutFactory {


    OrderedLayoutFactory(Class<? extends AbstractOrderedLayout> componentClass) {
        super(componentClass)
    }

    @Override
    protected void setExpandRatioFrom(ComponentFactory childFactory) {
        if (savedAttributes.containsKey('expandRatio')) {
            Float ratio
            Object ratioValue = savedAttributes['expandRatio']
            try {
                ratio = ratioValue as Float
            } catch (GroovyCastException e) {
                throw new VaadinBuilderException("The ${ratioValue.getClass().simpleName} value '$ratioValue' cannot be converted to an expand ration. It must be a number", e)
            }
            assert component instanceof AbstractOrderedLayout
            component.setExpandRatio(childFactory.component, ratio)
        }
    }
}