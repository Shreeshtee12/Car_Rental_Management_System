import Controller.AddRatingController;
import Controller.CarController;
import Controller.ClientController;
import Controller.RentingController;


import View.*;

public class MainClass {
    public static void main(String[] args) {
        ClientsPanel clientsPanel = new ClientsPanel();
        CarsPanel carsPanel = new CarsPanel();
        RentalsPanel rentalsPanel = new RentalsPanel();
        AddRantingPanel addRantingPanel = new AddRantingPanel();
        HomePanel home = new HomePanel();
        UI ui = new UI(clientsPanel, carsPanel, rentalsPanel, addRantingPanel, home);

        ClientController clientController = new ClientController(clientsPanel);
        CarController carController = new CarController(carsPanel);
        RentingController rentingController = new RentingController(rentalsPanel,addRantingPanel );
        AddRatingController addRantingPane1 = new AddRatingController(addRantingPanel);

        ui.setVisible(true);
    }
}
