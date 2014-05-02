/*
 * Copyright (c) 2014 Prajna Inc.
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

package com.prajnainc.vaadinbuilder.support

import com.prajnainc.vaadinbuilder.VaadinBuilderException
import com.vaadin.data.Property
import com.vaadin.data.util.AbstractProperty

/**
 * GroovyObjectProperty
 *
 *
 */
class GroovyObjectProperty extends AbstractProperty {

    GroovyObject instance
    String name

    public GroovyObjectProperty(instance,name,readOnly=false) {
        def metaProperty = instance.metaClass.getMetaProperty(name)
        if(!metaProperty) throw new VaadinBuilderException("$instance has no property '$name'")

        this.instance = instance
        this.name = name
        super.setReadOnly(readOnly || (metaProperty.setter == null))
    }

    @Override
    void setReadOnly(boolean newStatus) {
        // Only set read-only if there is a setter on the instance
        if(instance.metaClass.getMetaProperty(name).setter != null) {
            super.setReadOnly(newStatus)
        }
    }

    @Override
    Object getValue() {
        return instance.getProperty(name)
    }

    @Override
    void setValue(Object newValue) throws Property.ReadOnlyException {
        if(readOnly) {
            throw new Property.ReadOnlyException("$name on $instance is read-only")
        }
        instance.setProperty(name,newValue)
        fireValueChange()
    }

    @Override
    Class getType() {
        return instance.metaClass.getMetaProperty(name).type
    }

}
