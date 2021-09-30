package com.sklabs.hotelcheckin;

public class Bookings {
    String Username;
    String Date;
    Long RoomNumber;
    String Time;
    Long BookingID;

    public Bookings(String username, String date, Long roomNumber, String time, Long BookingID) {
        Username = username;
        Date = date;
        RoomNumber = roomNumber;
        Time = time;
        this.BookingID = BookingID;
    }

    public Bookings() {
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Long getRoomNumber() {
        return RoomNumber;
    }

    public void setRoomNumber(Long roomNumber) {
        RoomNumber = roomNumber;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public Long getBookingID() {
        return BookingID;
    }

    public void setBookingID(Long bookingID) {
        BookingID = bookingID;
    }
}
