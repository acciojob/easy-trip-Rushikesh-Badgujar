package com.driver.controllers;
import com.driver.model.Flight;
import com.driver.model.Airport;
import com.driver.model.Passenger;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class AirportRepository {
    Map<String,Airport> airportMap = new HashMap<>();
    Map<Integer,Flight> flightMap = new HashMap<>();
    Map<Integer,Passenger> passengerMap = new HashMap<>();
    Map<Integer,List<Integer>> flightPassengerMap = new HashMap<>();
    Map<Integer,Integer> flightCancelMap = new HashMap<>();

    public void saveAirport(Airport airport){
        airportMap.put(airport.getAirportName(),airport);
    }

    public List<Airport> getAllAirport(){
        return new ArrayList<>(airportMap.values());
    }

    public List<Airport> getAirportByTerminal(int noOfTerminal){
        List<Airport> airportList = new ArrayList<>();
        for(String airportName : airportMap.keySet()){
            Airport airport = airportMap.get(airportName);
            if(airport.getNoOfTerminals() == noOfTerminal){
                airportList.add(airport);
            }
        }
        return airportList;
    }

    public void saveFlight(Flight flight){
        flightMap.put(flight.getFlightId(),flight);
    }

    public List<Flight> getAllFlight(){
        if(flightMap.isEmpty()) return new ArrayList<>();
        return new ArrayList<>(flightMap.values());
    }

    public Flight getFlightById(Integer flightId){
        if(flightMap.containsKey(flightId)){
            return flightMap.get(flightId);
        }
        return null;
    }

    public void savePassenger(Passenger passenger){
        passengerMap.put(passenger.getPassengerId(),passenger);
    }
    public Passenger getPassengerById(Integer passengerId){
        if(passengerMap.containsKey(passengerId)){
            return passengerMap.get(passengerId);
        }
        return null;
    }

    public String bookAtTicket(Integer flightId,Integer passengerId){

        if(flightPassengerMap.containsKey(flightId)){
            List<Integer> passengerList = flightPassengerMap.get(flightId);
            if(passengerList.contains(passengerId)){
                return "FAILURE";
            }
            passengerList.add(passengerId);
            flightPassengerMap.put(flightId,passengerList);
        }
        else {
            List<Integer> newPassengerList = new ArrayList<>();
            newPassengerList.add(passengerId);
            flightPassengerMap.put(flightId,newPassengerList);
        }
        return "SUCCESS";
    }
    public int numberOfTicketForFlight(Integer flightId){
        if(flightPassengerMap.containsKey(flightId)){
            return flightPassengerMap.get(flightId).size();
        }
        else return 0;
    }
    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId){
        int count = 0;
        for(Integer flightId : flightPassengerMap.keySet()){
            List<Integer> passengerList = flightPassengerMap.get(flightId);
            for(Integer pId : passengerList){
                if(pId == passengerId){
                    count++;
                }
            }
        }
        return count;
    }


    public List<Integer> getAirportByName(String airportName){
        if(airportMap.containsKey(airportName)){
            return (List<Integer>) airportMap.get(airportName);
        }
        else return null;
    }
    
    public List<Integer> getAllBookingWithFlightId(){
        return new ArrayList<>(flightPassengerMap.keySet());
    }
    public List<Integer> getBookingPassengersByFlightIds(Integer flightId) {
        return flightPassengerMap.get(flightId);
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {

        List<Integer> pIds = flightPassengerMap.get(flightId);
        if(!pIds.contains(passengerId)) return "FAILURE";
        List<Integer> newList = new ArrayList<>();
        int n = pIds.size();
        for(int i = 0 ; i < n ; i++ ){
            if(pIds.get(i) == passengerId){
                newList.add(i);
            }
        }
        for(int i = 0 ; i < newList.size() ; i++){
            int idx = newList.get(i);
            pIds.remove(idx);

        }

        flightCancelMap.put(flightId,flightCancelMap.getOrDefault(0,1)+1);
        return "SUCCESS";

    }

    public int getCancelBookings(Integer flightId) {
        if(flightCancelMap.containsKey(flightId)){
            return flightCancelMap.get(flightId);
            //  return 1;
        }
        else return  1;
    }








}
