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

package com.prajnainc.vaadinbuilder.samplerapp

import com.prajnainc.vaadinbuilder.ui.BuilderUI
import com.vaadin.server.BrowserWindowOpener
import com.vaadin.server.Sizeable
import com.vaadin.server.VaadinRequest
import com.vaadin.shared.ui.MarginInfo
import com.vaadin.ui.Button
import groovy.beans.Bindable

/**
 *
 */
class SamplerUI extends BuilderUI {

    static class Person {
        String name
        String address
        String city
        String state
        String zip
        Integer age

        public String toString() {
            return
"""Name:           $name
Adddress:       $address
City:           $city
State + Zip:    $state $zip
Age:            $age"""
        }
    }

    @Bindable person

    @Override
    def getViewDefinition() {
        return {
            panel(caption: 'Vaadin Builder Sampler') {
                tabSheet() {
                    fieldGroup(id: 'personForm', caption: 'Form', dataSource: bind(source: this, sourceProperty: 'person')) {
                        field('name')
                        field('address')
                        field('city')
                        field('state')
                        field('zip')
                        field('age')
                        horizontalLayout(width: '100%', height: Sizeable.SIZE_UNDEFINED) {
                            button(id: 'saveButton', caption: 'Save')
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        super.init(vaadinRequest)

        Button b = builder.saveButton
        b.addClickListener([buttonClick: {evt ->
            builder.personForm.commit()
            println person
        }] as Button.ClickListener)


    }

}
