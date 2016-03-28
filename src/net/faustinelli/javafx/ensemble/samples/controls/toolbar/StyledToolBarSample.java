/*
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.faustinelli.javafx.ensemble.samples.controls.toolbar;

import net.faustinelli.javafx.ensemble.Sample;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.control.ToolBarBuilder;
import javafx.scene.layout.VBoxBuilder;

/**
 * A sample that shows three ToolBars, two of which are styled specifically with
 * a separate CSS style sheet.
 *
 * @see javafx.scene.control.ToolBar
 * @related controls/toolbar/ToolBar
 * @resource StyledToolBar.css
 */
public class StyledToolBarSample extends Sample {

    public StyledToolBarSample() {
        ToolBar standardToolbar = createToolBar("standard");

        String styledToolBarCss = StyledToolBarSample.class.getResource("StyledToolBar.css").toExternalForm();
        
        ToolBar darkToolbar = createToolBar("dark");        
        darkToolbar.getStylesheets().add(styledToolBarCss);

        ToolBar blueToolbar = createToolBar("blue");
        blueToolbar.getStylesheets().add(styledToolBarCss);

        getChildren().add(VBoxBuilder.create().spacing(10).padding(new Insets(10)).children(standardToolbar, darkToolbar, blueToolbar).build());
    }

    private ToolBar createToolBar(String id) {
        return ToolBarBuilder.create().id(id).items(
                new Button("Button 1"),
                new Button("Button 2"),
                new Slider()).build();
    }
}