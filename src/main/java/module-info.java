module  bestSokobanEverV6{
    requires java.base;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;
    requires java.desktop;
    requires java.logging;
	requires javafx.base;
	requires javafx.swing;
	requires junit;
    opens display;
    opens element;
    opens interact.controller;
    opens interact.model;
    opens interact.view;
}