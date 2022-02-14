# Introduction
This is an Android app which could detect the kinds of food it sees. The app also provide recommended recipe for that kind of food. 
# Main functions
* Food recognition: we use a pre-built TensorFlow Lite model to estimate the confidence level of a dish to be a specific food. We choose the three highest confidence levels to display on the screen. The model could be found at credit section.
* Recipe and nutrients search: we search for a recipe and nutrients for the food the users want to see. We call the api from https://www.edamam.com/ to get the result in json and display.
# Credit
* Google's food classification model: https://tfhub.dev/google/lite-model/aiy/vision/classifier/food_V1/1
* Food recipe API: https://www.edamam.com/ 
# Document
https://docs.google.com/document/d/1C9IPS6h5lU094I2xCOv2Df7Ce3iWEM9hPnsYkh8VfHE/edit?usp=sharing
