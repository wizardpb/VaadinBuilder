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
 *
 *
 */
package com.prajnainc.vaadinbuilder.factories

import com.vaadin.ui.TabSheet


/**
 * TabSheetFactory
 *
 */
class TabSheetFactory extends ComponentContainerFactory {

    TabSheetFactory() {
        super(TabSheet)
    }

    @Override
    void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        VaadinFactory childFactory = builder.childBuilder.currentFactory
        def caption = childFactory.savedAttributes[TAB_CAPTION]
        // TODO icon
        if(caption) parent.addTab(child, caption) else parent.addTab(child)
    }
}