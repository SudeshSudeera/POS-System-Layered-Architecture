package com.devstack.pos.controller;

import com.devstack.pos.Dto.*;
import com.devstack.pos.bo.BoFactory;
import com.devstack.pos.bo.custom.CustomerBo;
import com.devstack.pos.bo.custom.LoyaltyCardBo;
import com.devstack.pos.bo.custom.OrderDetailBo;
import com.devstack.pos.bo.custom.ProductDetailBo;
import com.devstack.pos.enums.BoType;
import com.devstack.pos.enums.CardType;
import com.devstack.pos.util.QrDataGenerator;
import com.devstack.pos.util.UserSessionData;
import com.devstack.pos.view.tm.CartTm;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.codec.binary.Base64;

import javax.smartcardio.Card;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.CacheRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static sun.security.krb5.Config.refresh;

public class PlaceOrderFormController {

    public AnchorPane context;
    public ToggleGroup model;
    public TextField txtEmail;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtSalary;
    public Hyperlink urlNewLoyalty;
    public Label lblLoyaltyType;
    public TextField txtBarcode;
    public TextField txtDescription;
    public TextField txtSellingPrice;
    public TextField txtDiscount;
    public TextField txtShowPrice;
    public TextField txtBuyingPrice;
    public Label lblDiscountAv;
    public TextField txtQty;
    public TextField txtQtyOnHand;
    public TableView<CartTm> tblCart;
    public TableColumn colCode;
    public TableColumn colDesc;
    public TableColumn colSellingPrice;
    public TableColumn colDiscount;
    public TableColumn colShowPrice;
    public TableColumn colQty;
    public TableColumn colTotalCost;
    public TableColumn colOperation;
    public Label txtTotal;

    CustomerBo bo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private ProductDetailBo productDetailBo = BoFactory.getInstance().getBo(BoType.PRODUCT_DETAIL);
    private OrderDetailBo orderDetailBo = BoFactory.getInstance().getBo(BoType.ORDER_DETAIL);
    private LoyaltyCardBo loyaltyCardBo = BoFactory.getInstance().getBo(BoType.LOYALTY_CARD);

    public void initialize(){
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colShowPrice.setCellValueFactory(new PropertyValueFactory<>("showPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        colOperation.setCellValueFactory(new PropertyValueFactory<>("btn"));

    }
    public void btnBackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm", false);
    }

    public void btnAddNewCustomerOnAction(ActionEvent actionEvent) throws IOException {
        setUi("CustomerForm", true);
    }

    public void btnAddNewProductOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ProductMainForm", true);
    }

    public void btnCompleteOrderOnAction(ActionEvent actionEvent) {
        ArrayList<ItemDetailDto> dtoList = new ArrayList<>();
        double discount = 0;
        for (CartTm tm: tms){
            dtoList.add(new ItemDetailDto(
                    tm.getCode(),tm.getQty(),tm.getDiscount(),tm.getTotalCost()
            ));
            discount+=tm.getDiscount();
        }

        OrderDetailDto dto = new OrderDetailDto(
                new Random().nextInt(100001),new Date(),
                Double.parseDouble(txtTotal.getText().split(" /=")[0]),
                txtEmail.getText(), discount,
                UserSessionData.email,dtoList
        );
        try {
            if(orderDetailBo.makeOrder(dto)){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Saved").show();
                clearFields();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try Again").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
            e.printStackTrace();
        }
    }

    private void clearFields() {
    }

    private void setUi(String url, boolean state) throws IOException {
        Stage stage = null;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")));
        if (state){
            stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }else{
            stage = (Stage)context.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        }
    }

    public void newLoyaltyOnAction(ActionEvent actionEvent) {
        try {
            double salary = Double.parseDouble(txtSalary.getText());

            CardType type = null;
            if (salary >= 100000){
                type = CardType.PLATINUM;
            }else if (salary >= 50000){
                type = CardType.GOLD;
            }else{
                type = CardType.SILVER;
            }


            String uniqueData = QrDataGenerator.generate(25);
            //----------------------Gen QR
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(
                    qrCodeWriter.encode(uniqueData, BarcodeFormat.QR_CODE, 160,160)
            );
            //----------------------Gen QR
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            javax.imageio.ImageIO.write(bufferedImage,"png",baos);
            byte[] arr = baos.toByteArray();


            if (urlNewLoyalty.getText().equals("+ New Loyalty")){
                boolean isLoyaltyCardAssigned = loyaltyCardBo.saveLoyaltyData(
                        new LoyaltyCardDto(
                                new Random().nextInt(10001),
                                type,
                                Base64.encodeBase64String(arr),
                                txtEmail.getText()
                        )
                );
                if (isLoyaltyCardAssigned){
                    new Alert(Alert.AlertType.CONFIRMATION,"Saved").show();
                    urlNewLoyalty.setText("Show Loyalty Card Information");
                }else{
                    new Alert(Alert.AlertType.WARNING,"Try Again Shortly").show();
                }
            }else{
                // view data (did in new batch seen)
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"Try Again Shortly").show();
            throw new RuntimeException(e);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void searchCustomer(ActionEvent actionEvent) {
        try {
            CustomerDto customer = bo.findCustomer(txtEmail.getText());
            if (customer!=null){

                txtName.setText(customer.getName());
                txtSalary.setText(String.valueOf(customer.getSalary()));
                txtContact.setText(customer.getContact());

                fetchLoyaltyCardData(txtEmail.getText());

            }else{
                new Alert(Alert.AlertType.WARNING,"Can't find the customer").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
            throw new RuntimeException(e);
        }
    }

    private void fetchLoyaltyCardData(String email) {
        urlNewLoyalty.setText("+ New Loyalty");
        urlNewLoyalty.setVisible(true);

    }

    public void loadProduct(ActionEvent actionEvent) {
        try {
            ProductDetailJoinDto pd = productDetailBo.findProductJoinDetail(
                    txtBarcode.getText()
            );
            if (pd != null){
                txtDescription.setText(pd.getDescription());
                txtDiscount.setText(String.valueOf(250));
                txtSellingPrice.setText(String.valueOf(pd.getDto().getSellingPrice()));
                txtShowPrice.setText(String.valueOf(pd.getDto().getShowPrice()));
                txtBuyingPrice.setText(String.valueOf(pd.getDto().getBuyingPrice()));
                txtQtyOnHand.setText(String.valueOf(pd.getDto().getQtyOnHand()));
                txtQty.requestFocus();

            }else{
                new Alert(Alert.AlertType.WARNING,"Can't find the Product").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
            throw new RuntimeException(e);
        }

    }

    ObservableList<CartTm> tms = FXCollections.observableArrayList();
    public void addToCart(ActionEvent actionEvent) {
        int qty = Integer.parseInt(txtQty.getText());

        /*if(customer.cardType.equals(CardType.GOLD.name())){
            // discount logic
        }*/
        double discount = 250; //=>

        double sellingPrice = (Double.parseDouble(txtSellingPrice.getText())-discount);
        double totalCost = qty*sellingPrice;

        CartTm selectedCartTm = isExists(txtBarcode.getText());
        if (selectedCartTm != null){
            selectedCartTm.setQty(qty+selectedCartTm.getQty());
            selectedCartTm.setTotalCost(totalCost+selectedCartTm.getTotalCost());
            //tblCart.refresh();

        }else{
            Button btn = new Button("Remove");

            CartTm tm = new CartTm(
                    txtBarcode.getText(),
                    txtDescription.getText(),
                    Double.parseDouble(txtDiscount.getText()),
                    sellingPrice,
                    Double.parseDouble(txtShowPrice.getText()),
                    qty,
                    totalCost,
                    btn
            );

            btn.setOnAction((e) -> {
                tms.remove(tm);
                //tblCart.refresh();
                setTotal();
            });

            tms.add(tm);
            clear();
            tblCart.setItems(tms);
            setTotal();
        }
    }

    private void clear() {
        txtDescription.clear();
        txtSellingPrice.clear();
        txtDiscount.clear();
        txtShowPrice.clear();
        txtBuyingPrice.clear();
        txtQty.clear();
        txtQtyOnHand.clear();
        txtBarcode.clear();
        txtBarcode.requestFocus();
    }

    private CartTm isExists(String code){
        for (CartTm tm:tms) {
            if(tm.getCode().equals(code)){
                return tm;
            }
        }
        return null;
    }

    public void setTotal(){
        double total = 0;
        for (CartTm tm:tms) {
            total += tm.getTotalCost();
        }
        txtTotal.setText(total + " /=");
    }

}
