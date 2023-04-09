package com.example.csci3130courseproject.UI.User.Rating;

import com.example.csci3130courseproject.UI.User.IUser;

public interface IRate {

    public double getUserRating(IUser user);

    public double updateUserRating(IUser user, double rating);

}
