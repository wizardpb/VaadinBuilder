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

package com.prajnainc.vaadinbuilder.binding

import com.vaadin.data.util.IndexedContainer
import com.vaadin.ui.ComboBox
import groovy.beans.Bindable;
import spock.lang.*
import static org.hamcrest.CoreMatchers.*
import static spock.util.matcher.HamcrestSupport.*

public class SelectContainerBindingSpecification extends Specification {

    static class Model {
        @Bindable def modelProp
    }

    def "it should bind a property containing a value"() {

        given:
        def model = new Model(modelProp: 1..5)
        def ComboBox target = new ComboBox()
        new SelectContainerBinding(source: model, sourceProperty: 'modelProp').bind(target)

        expect:
        that target.containerDataSource, instanceOf(IndexedContainer)
        that target.containerDataSource.itemIds, equalTo(1..5 as List)
    }


    def "it can set a target and bind"() {

        given:
        def model = new Model(modelProp: 1..5)
        def ComboBox target = new ComboBox()
        new SelectContainerBinding(source: model, sourceProperty: 'modelProp',target: target).bind()

        expect:
        that target.containerDataSource, instanceOf(IndexedContainer)
        that target.containerDataSource.itemIds, equalTo(1..5 as List)
    }

    def "it will maintain the binding when the source property changes"() {

        given:
        def model = new Model(modelProp: 1..5)
        def ComboBox target = new ComboBox()
        new SelectContainerBinding(source: model, sourceProperty: 'modelProp').bind(target)
        model.modelProp = 'a'..'d'

        expect:
        that target.containerDataSource.itemIds, equalTo('a'..'d' as List)
    }

    def "it can bind a source directly"() {

        given:
        def ComboBox target = new ComboBox()
        new SelectContainerBinding(source: 1..5 as List).bind(target)

        expect:
        that target.containerDataSource.itemIds, equalTo(1..5 as List)
    }

    def "it adds when the source property contents is observable and adds"() {
        given:
        def model = new Model(modelProp: new ObservableList(1..5 as ArrayList))
        def ComboBox target = new ComboBox()
        new SelectContainerBinding(source: model, sourceProperty: 'modelProp').bind(target)
        model.modelProp.add(6)

        expect:
        that target.containerDataSource, instanceOf(IndexedContainer)
        that target.containerDataSource.itemIds, equalTo(1..6 as List)
    }

    def "it adds when the source contents is observable and adds"() {
        given:
        def model = new ObservableList(1..5 as ArrayList)
        def ComboBox target = new ComboBox()
        new SelectContainerBinding(source: model).bind(target)
        model.add(6)

        expect:
        that target.containerDataSource, instanceOf(IndexedContainer)
        that target.containerDataSource.itemIds, equalTo(1..6 as List)
    }

    def "it removes when the source property contents is observable and changes"() {
        given:
        def model = new Model(modelProp: new ObservableList(1..5 as ArrayList))
        def ComboBox target = new ComboBox()
        new SelectContainerBinding(source: model, sourceProperty: 'modelProp').bind(target)
        model.modelProp.remove(3)

        expect:
        that target.containerDataSource, instanceOf(IndexedContainer)
        that target.containerDataSource.itemIds, equalTo([1,2,3,5])
    }

    def "it removes when the source contents is observable and changes"() {
        given:
        def model = new ObservableList(1..5 as ArrayList)
        def ComboBox target = new ComboBox()
        new SelectContainerBinding(source: model).bind(target)
        model.remove(3)

        expect:
        that target.containerDataSource, instanceOf(IndexedContainer)
        that target.containerDataSource.itemIds, equalTo([1,2,3,5])
    }
}
