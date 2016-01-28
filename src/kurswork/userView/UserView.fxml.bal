<?xml version="1.0" encoding="UTF-8"?>

<?import java.lang.*?>
<?import java.util.*?>
<?import javafx.scene.*?>
<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>

<AnchorPane id="AnchorPane" maxWidth="700.0" minHeight="400.0" minWidth="600.0" prefHeight="525.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:controller="kurswork.userView.UserViewController">
   <children>
      <TabPane fx:id="megaTabPane" layoutY="40.0" minWidth="400.0" prefHeight="379.0" prefWidth="579.0" tabClosingPolicy="UNAVAILABLE" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="40.0">
        <tabs>
          <Tab text="User Info">
            <content>
              <AnchorPane minHeight="0.0" minWidth="400.0" prefHeight="363.0" prefWidth="600.0">
                     <children>
                        <Label layoutX="15.0" layoutY="14.0" text="User name: " />
                        <Label fx:id="user_name_info" layoutX="126.0" layoutY="14.0" text="_USER_NAME_INFO_" />
                        <Label layoutX="12.0" layoutY="109.0" text="User mobile phone: " />
                        <TextField editable="false" layoutX="122.0" layoutY="68.0" prefHeight="25.0" prefWidth="146.0" promptText="your phone number" />
                        <TextField editable="false" layoutX="126.0" layoutY="105.0" prefHeight="25.0" prefWidth="149.0" promptText="your email adress" />
                        <Label layoutX="16.0" layoutY="72.0" text="User email: " />
                        <Separator layoutX="6.0" layoutY="35.0" prefWidth="200.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" />
                        <Separator layoutY="65.0" prefWidth="200.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" />
                        <Separator layoutY="100.0" prefWidth="200.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" />
                        <Label layoutX="14.0" layoutY="38.0" text="user grup" />
                        <Label layoutX="122.0" layoutY="38.0" text="_USER_GRUP_INFO_" />
                        <Separator layoutY="139.0" prefWidth="200.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" />
                        <Button fx:id="MAINACTION" layoutX="534.0" layoutY="10.0" mnemonicParsing="false" text="Change Password" AnchorPane.rightAnchor="10.0" AnchorPane.topAnchor="5.0" />
                        <ScrollPane layoutX="19.0" layoutY="149.0" prefHeight="313.0" prefWidth="600.0" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="145.0">
                          <content>
                            <AnchorPane minHeight="0.0" minWidth="0.0" prefHeight="291.0" prefWidth="633.0">
                                 <children>
                                    <TableView prefHeight="255.0" prefWidth="343.0">
                                      <columns>
                                        <TableColumn prefWidth="75.0" text="C1" />
                                        <TableColumn prefWidth="75.0" text="C2" />
                                      </columns>
                                    </TableView>
                                 </children>
                              </AnchorPane>
                          </content>
                        </ScrollPane>
                     </children>
                  </AnchorPane>
            </content>
          </Tab>
        </tabs>
      </TabPane>
      <Label fx:id="qqq_q" layoutX="93.0" layoutY="6.0" text="qqq" />
      <Button fx:id="MA2" layoutX="282.0" layoutY="11.0" mnemonicParsing="false" text="Button" />
   </children>
</AnchorPane>
