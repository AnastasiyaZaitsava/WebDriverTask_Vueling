package com.epam;

import com.epam.bean.Person;
import com.epam.steps.Steps;
import org.testng.Assert;
import org.testng.annotations.*;

public class TestWD
    {
        private Steps steps;
        private final static String LOGIN = "mashkevich.nastya@mail.ru";
        private final static String PASSWORD = "wertyu1";
        private final static String CITYOFDEPARTURE = "Warsaw";
        private final static String CITYOFARRIVLE = "Barcelona";
        private final static String SERVICECOUNTRY = "Italy";
        private final static String DATEOFFLIGHT = "5/April";
        private final static String DATEFORWARD = "2/April";
        private final static String DATEBACK = "14/April";
        private final static String FULLFORMATFLIGHTDATE = "05/04/2017";
        private final static String FLIGHTNUMBER = "8845";
        private final static int NUMBEROFPESENGER = 2;
        private final static String CITY = "Madrid";
        private final static int FLIGHTWITHRETURN = 2;

        @BeforeTest(description = "Init browser")
        public void setUp()
        {
            steps = new Steps();
            steps.initBrowser();
        }

        @Test
        public void oneCanLoginGithub()
        {
            steps.loginToVueling(LOGIN, PASSWORD);
            Assert.assertTrue(steps.isLoginToVueling());
        }

        @Test
        public void oneCanChooseServiceCenter ()
        {
            Assert.assertTrue(steps.isChangeContactInfo(SERVICECOUNTRY));
        }

        @Test
        public void oneCanSearchFlight ()
        {
            steps.startWorkWithMainPage(CITYOFDEPARTURE, CITYOFARRIVLE, DATEFORWARD);
            Assert.assertTrue(steps.checkFlight(DATEBACK));
        }

        @Test
        public void oneCanFillInfoAboutPassenger ()
        {
            Person person = new Person("John", "Smith", "Minsk", "456783", "johnsmith@gmail.com", "BY", "+375");
            steps.startWorkWithMainPage(CITYOFDEPARTURE, CITYOFARRIVLE, DATEFORWARD);
            steps.fillPassengerInformation(person,DATEBACK);
            Assert.assertTrue(steps.isFillInfoCorrect(person));
        }

        @Test
        public void oneCanCheckStatusFlight ()
        {
            steps.startWorkWithFlightsStatusPageWithFlightNumber(FLIGHTNUMBER, DATEOFFLIGHT);
            Assert.assertEquals(steps.checkFlightStatus(),("Not operating"));
            Assert.assertEquals(steps.checkDateFlight(), DATEOFFLIGHT);
            Assert.assertEquals(steps.checkCityDeparture (), CITYOFDEPARTURE);
            Assert.assertEquals(steps.checkCityArrival (), CITYOFARRIVLE);
        }

        @Test
        public void oneCanCheckWrightPriceForTwoPassengerFlightOneWay ()
        {
            steps.canChooseFlightOneWay(CITYOFDEPARTURE, CITYOFARRIVLE, DATEFORWARD);
            Assert.assertEquals((steps.takePriceFromWebSiteFor1Passenger()*NUMBEROFPESENGER),steps.takeTotalPriceForAllPassenger());
        }

        @Test
        public void oneCanCheckRightPriceForTwoPassengerFlightWithReturn ()
        {
            steps.canChooseFlightWithReturn(CITYOFDEPARTURE, CITYOFARRIVLE, DATEFORWARD, DATEBACK);
            Assert.assertEquals((steps.takePriceFromWebSiteFor1Passenger()*NUMBEROFPESENGER) + steps.takeBookingFee(), steps.takeFinalPrice());
        }

        @Test
        public void canFindAirport()
        {
            steps.checkAirport(CITY);
            Assert.assertTrue(steps.isAirportFound());
        }

        @Test
        public void oneCanCheckAddLuggage() {
            Person person = new Person("John", "Smith", "Minsk", "456783", "johnsmith@gmail.com", "BY", "+375");
            steps.startWorkWithMainPage(CITYOFDEPARTURE, CITYOFARRIVLE, DATEFORWARD);
            steps.addLuggage(person, DATEBACK);
            Assert.assertEquals(steps.takePriceForPassengerWithLuggage(), steps.takePriceForPassengerWithLuggageFromTable());
        }

        @Test
        public void oneCanCheckAddSeat() throws InterruptedException {
            Person person = new Person("John", "Smith", "Minsk", "456783", "johnsmith@gmail.com", "BY", "+375");
            steps.startWorkWithMainPage(CITYOFDEPARTURE, CITYOFARRIVLE, DATEFORWARD);
            steps.addSeat(person, DATEBACK);
            Assert.assertEquals(steps.takeTotalPriceForPassengerWithSeats(), steps.takePriceForPassengerWithSeatsFromTable());

        }

        @Test
        public void oneCanCheckStatusFlightByDestinations()
        {
            steps.startWorkWithFlightsStatusPageWithDestinations(CITYOFDEPARTURE, CITYOFARRIVLE, DATEOFFLIGHT);
            if(steps.severalFlightsTableDisplayed())
            {
                Assert.assertTrue(steps.correctMultipleFlightsInfoDisplayed(CITYOFDEPARTURE,CITYOFARRIVLE,FULLFORMATFLIGHTDATE));
            }
            else{
                Assert.assertEquals(steps.checkFlightStatus(),("Not operating"));
                Assert.assertEquals(steps.checkDateFlight(), DATEOFFLIGHT);
                Assert.assertEquals(steps.checkCityDeparture (), CITYOFDEPARTURE);
                Assert.assertEquals(steps.checkCityArrival (), CITYOFARRIVLE);
            }
        }

        @Test
        public void oneCanFindHotels()  {
            Assert.assertTrue(steps.enterHotelParameters(CITYOFARRIVLE));
            Assert.assertTrue(steps.isHotelsFound());
        }

        @AfterTest(description = "Stop Browser")
         public void stopBrowser()
         {
             steps.closeDriver();
         }

    }