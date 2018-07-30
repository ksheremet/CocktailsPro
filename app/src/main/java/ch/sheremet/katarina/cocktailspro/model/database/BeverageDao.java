package ch.sheremet.katarina.cocktailspro.model.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import ch.sheremet.katarina.cocktailspro.model.Beverage;
import ch.sheremet.katarina.cocktailspro.model.BeverageDetails;
import ch.sheremet.katarina.cocktailspro.model.Ingredients;

@Dao
public abstract class BeverageDao {
    @Query("SELECT * FROM beverage_table")
    public abstract LiveData<List<Beverage>> getAllBeverages();

    @Transaction
    public void insert(Beverage beverage, BeverageDetails beverageDetails) {
        insertBeverage(beverage);
        insertBeverageDetails(beverageDetails);
        insertAllIngredients(beverageDetails.getIngredients());
    }

    @Transaction
    public void delete(Beverage beverage, BeverageDetails beverageDetails) {
        deleteBeverage(beverage);
        deleteBeverageDetails(beverageDetails);
        deleteAllIngredients(beverage.getId());
    }

    @Transaction
    public BeverageDetails getBeverageDetailsWithIngredients(String id) {
        List<Ingredients> ingredientsList = getAllIngredients(id);
        BeverageDetails beverageDetails = getBeverageDetail(id);
        beverageDetails.setIngredients(ingredientsList);
        return beverageDetails;
    }

    @Insert
    abstract void insertBeverage(Beverage beverage);

    @Delete
    abstract void deleteBeverage(Beverage beverage);

    @Query("SELECT * FROM beverage_table WHERE id=:id")
    public abstract Beverage getBeverageById(String id);

    @Query("SELECT * FROM beverage_detail_table WHERE id=:id")
    abstract BeverageDetails getBeverageDetail(String id);

    @Insert
    abstract void insertBeverageDetails(BeverageDetails beverageDetails);

    @Delete
    abstract void deleteBeverageDetails(BeverageDetails beverageDetails);

    @Query("SELECT * FROM ingredient_table WHERE beverage_id=:id")
    abstract List<Ingredients> getAllIngredients(String id);


    @Query("DELETE FROM ingredient_table WHERE beverage_id=:id")
    abstract void deleteAllIngredients(String id);

    @Insert()
    abstract void insertAllIngredients(List<Ingredients> ingredients);

}
