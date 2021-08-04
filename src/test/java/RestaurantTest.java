import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    public void setUp() {
        restaurant = new Restaurant("Amelie's cafe", "Chennai", LocalTime.parse("10:30:00"), LocalTime.parse("22:00:00"));
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //Arrange
        LocalTime currentTime = LocalTime.parse("11:00:00");
        Restaurant spiedRestraurant = Mockito.spy(restaurant);
        Mockito.when(spiedRestraurant.getCurrentTime()).thenReturn(currentTime);

        //Act
        boolean actual = spiedRestraurant.isRestaurantOpen();

        //Assert
        assertEquals(true, actual);
        verify(spiedRestraurant, times(2)).getCurrentTime();
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //Arrange
        LocalTime currentTime = LocalTime.parse("23:00:00");
        Restaurant spiedRestraurant = Mockito.spy(restaurant);
        Mockito.when(spiedRestraurant.getCurrentTime()).thenReturn(currentTime);

        //Act
        boolean actual = spiedRestraurant.isRestaurantOpen();

        //Assert
        assertEquals(false, actual);
        verify(spiedRestraurant, times(2)).getCurrentTime();
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        //Arrange
        int initialMenuSize = restaurant.getMenu().size();

        // Act
        restaurant.addToMenu("Sizzling brownie",319);

        //Assert
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        //Arrange
        int initialMenuSize = restaurant.getMenu().size();

        //Act
        restaurant.removeFromMenu("Vegetable lasagne");

        //Assert
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        //Assert
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }

    @Test
    public void get_zero_cost_if_no_item_is_selected()
    {
        // Arrange
        List<String> itemsName = new ArrayList<String>();

        //Act
        int actual = restaurant.getTotalItemsCost(itemsName);

        //Assert
        assertEquals(0, actual);
    }

    @Test
    public void get_correct_cost_when_one_or_more_items_are_selected()
    {
        //Arrange
        List<String> itemsName = new ArrayList<String>();
        itemsName.add("Sweet corn soup");
        itemsName.add("Vegetable lasagne");

        //Act
        int actual = restaurant.getTotalItemsCost(itemsName);

        //Assert
        assertEquals(388, actual);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}