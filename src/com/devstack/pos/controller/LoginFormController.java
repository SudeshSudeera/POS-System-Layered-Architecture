package com.devstack.pos.controller;

import com.devstack.pos.Dto.UserDto;
import com.devstack.pos.bo.BoFactory;
import com.devstack.pos.bo.custom.UserBo;
import com.devstack.pos.bo.custom.impl.UserBoImpl;
import com.devstack.pos.enums.BoType;
import com.devstack.pos.util.PasswordManager;
import com.devstack.pos.util.UserSessionData;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LoginFormController {

    public AnchorPane context;
    public JFXTextField txtEmail;
    public JFXPasswordField txtPassword;

    UserBo bo = BoFactory.getInstance().getBo(BoType.USER);

    public void btnSigninOnAction(ActionEvent actionEvent) {
        try {
            UserDto ud = bo.findUser(txtEmail.getText());
            if (ud != null){
                if (PasswordManager.checkPassword(txtPassword.getText(),ud.getPassword())){
                    UserSessionData.email = txtEmail.getText();
                    setUi("DashboardForm");
                }else{
                    new Alert(Alert.AlertType.WARNING, "Check your Password").show();

                }
            }else{
                new Alert(Alert.AlertType.WARNING, "User Email Not Found").show();
            }

        }catch (ClassNotFoundException | SQLException | IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnCreateAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SignupForm");
    }

    private void setUi(String url) throws IOException {
        Stage stage = (Stage)context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml"))));
        stage.centerOnScreen();
    }
}
