package com.mroz.mateusz.weatherapplication.Weather;


import com.mroz.mateusz.weatherapplication.R;

public class Weather {
    private CurrentWeather mCurrentWeather;
    private HourlyWeather[] mHourlyWeather;
    private DailyWeather[] mDailyWeather;

    public CurrentWeather getCurrentWeather() {
        return mCurrentWeather;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        mCurrentWeather = currentWeather;
    }

    public HourlyWeather[] getHourlyWeather() {
        return mHourlyWeather;
    }

    public void setHourlyWeather(HourlyWeather[] hourlyWeather) {
        mHourlyWeather = hourlyWeather;
    }

    public DailyWeather[] getDailyWeather() {
        return mDailyWeather;
    }

    public void setDailyWeather(DailyWeather[] dailyWeather) {
        mDailyWeather = dailyWeather;
    }

    public static int getIconId(String iconLength, int dayOrNight) {
        String iconNumber;
        if(iconLength.length()==41) {
            iconNumber = iconLength.substring(34, 37);
        }
        else iconNumber = iconLength.substring(36, 39);

        int number = Integer.parseInt(iconNumber);
        int iconID;

        switch(number){
            case 113: {
                if(dayOrNight == 1) {
                    iconID = R.drawable.sun;
                }
                else iconID = R.drawable.moon;
            } break;
            case 116: {
                if(dayOrNight == 1) {
                    iconID = R.drawable.partly_cloudy_day;
                }
                else iconID = R.drawable.partly_cloudy_night;
            } break;
            case 119: {
                if(dayOrNight == 1) {
                    iconID = R.drawable.clouds;
                }
                else iconID = R.drawable.partly_cloudy_night;
            } break;
            case 122: iconID = R.drawable.clouds; break;
            case 143: {
                if(dayOrNight == 1) {
                    iconID = R.drawable.fog_day;
                }
                else iconID = R.drawable.dust;
            } break;
            case 176: iconID = R.drawable.little_rain; break;
            case 179: iconID = R.drawable.little_snow; break;
            case 182: iconID = R.drawable.little_rain; break;
            case 185: iconID = R.drawable.little_rain; break;
            case 200: iconID = R.drawable.chance_of_storm; break;
            case 227: iconID = R.drawable.snow; break;
            case 230: iconID = R.drawable.snow_storm; break;
            case 248: iconID = R.drawable.dust; break;
            case 260: iconID = R.drawable.dust; break;
            case 263: iconID = R.drawable.rainy_weather; break;
            case 266: iconID = R.drawable.rainy_weather; break;
            case 281: iconID = R.drawable.sleet; break;
            case 284: iconID = R.drawable.sleet; break;
            case 293: iconID = R.drawable.little_rain; break;
            case 296: iconID = R.drawable.little_rain; break;
            case 299: iconID = R.drawable.little_rain; break;
            case 302: iconID = R.drawable.rain; break;
            case 305: iconID = R.drawable.rainy_weather; break;
            case 308: iconID = R.drawable.rainy_weather; break;
            case 311: iconID = R.drawable.rainy_weather; break;
            case 314: iconID = R.drawable.rainy_weather; break;
            case 317: iconID = R.drawable.sleet; break;
            case 320: iconID = R.drawable.sleet; break;
            case 323: iconID = R.drawable.little_snow; break;
            case 326: iconID = R.drawable.little_snow; break;
            case 329: iconID = R.drawable.little_snow; break;
            case 332: iconID = R.drawable.little_snow; break;
            case 335: iconID = R.drawable.little_snow; break;
            case 338: iconID = R.drawable.snow_storm; break;
            case 350: iconID = R.drawable.hail; break;
            case 353: iconID = R.drawable.little_rain; break;
            case 356: iconID = R.drawable.rainy_weather; break;
            case 359: iconID = R.drawable.rainy_weather; break;
            case 362: iconID = R.drawable.sleet; break;
            case 365: iconID = R.drawable.sleet; break;
            case 368: iconID = R.drawable.little_snow; break;
            case 371: iconID = R.drawable.snow_storm; break;
            case 374: iconID = R.drawable.hail; break;
            case 377: iconID = R.drawable.hail; break;
            case 386: iconID = R.drawable.chance_of_storm; break;
            case 389: iconID = R.drawable.storm; break;
            case 392: iconID = R.drawable.storm; break;
            case 395: iconID = R.drawable.storm; break;
            default: iconID= R.drawable.sun;
        }
        return iconID;
    }

}
