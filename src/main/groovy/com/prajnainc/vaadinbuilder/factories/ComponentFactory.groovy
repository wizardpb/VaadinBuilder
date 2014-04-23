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

import com.vaadin.ui.Component

/**
 *
 * ComponentFactory
 *
 * A {@link Factory} that creates instances of a Vaadin {@link Component}s
 */

class ComponentFactory extends AbstractFactory implements VaadinFactory {

    private static final ATTRIBUTES_TO_SAVE = ['expandRatio','alignment']

    Class componentClass
    Map savedAttributes
    String nodeName
    Component component

    ComponentFactory(Class<? extends Component> componentClass) {
        this.componentClass = componentClass
    }

    /**
     * Save any attributes for later processing. They are removed from the passed in attributes
     */
    protected void extractSavedAttributes(Map attributes) {
        savedAttributes = [:]
        ATTRIBUTES_TO_SAVE.each {
            if(attributes.containsKey(it)) savedAttributes[it] = attributes.remove(it)
        }
    }

    @Override
    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        // Save our node name for exception reporting
        nodeName = name

        // Do the attribute saved, then return a new instance of the component class. The builder will set all bean (Component) properties set in the remaining attributes
        extractSavedAttributes(attributes)

        if(componentClass.isAssignableFrom(value.getClass())) {
            component = value
        } else {
            component = value ? componentClass.newInstance(value) : componentClass.newInstance()
        }
        return component
    }

    @Override
    void setParent(FactoryBuilderSupport builder, Object parent, Object child) {
        def parentFactory = builder.parentFactory
        assert parentFactory instanceof ComponentFactory
        parentFactory.doAddChild(child)
    }

    public void doAddChild(Component child) {
        throw new UnsupportedOperationException("Components do not add children")
    }

    /**
     * Return a {@link String} attaching the nodeName from this {@link ComponentFactory} to the given node ({@link Component} object
     *
     * @param Component
     */
    public String namedNodeString(Component node) {
        return "$nodeName($node)" as String
    }

}
