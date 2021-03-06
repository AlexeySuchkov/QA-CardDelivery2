package ru.netology.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.generator.DataGenerator;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTests {
    DataGenerator dataGenerator = new DataGenerator();

    @BeforeEach
    void Setup() {
        open("http://localhost:9999");
    }

    @Test
    void submitRequest() {

        $("[placeholder='Город']").setValue(dataGenerator.createCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(dataGenerator.forwardDate(3));
        $("[name=name]").setValue(dataGenerator.createName());
        $("[name=phone]").setValue(dataGenerator.createCellPhone());
        $(".checkbox__box").click();
        $(".button__text").click();
        $(withText("Успешно")).shouldBe(visible);
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(dataGenerator.forwardDate(4));
        $(".button__text").click();
        $(withText("У вас уже запланирована встреча на другую дату. Перепланировать?")).shouldBe(visible);
        $("[data-test-id=replan-notification] button.button").click();
        $(withText("Успешно")).shouldBe(visible);
    }

    @Test
    void submitFalsePhoneRequest() {

        $("[placeholder='Город']").setValue(dataGenerator.createCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(dataGenerator.forwardDate(3));
        $("[name=name]").setValue(dataGenerator.createName());
        $("[name=phone]").setValue(dataGenerator.createFalsePhone());
        $(".checkbox__box").click();
        $(".button__text").click();
        $(withText("Проверьте правильность введенного номера")).shouldBe(visible);
    }

    @Test
    void submitWithoutCity() {
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(dataGenerator.forwardDate(3));
        $("[name=name]").setValue(dataGenerator.createName());
        $("[name=phone]").setValue(dataGenerator.createCellPhone());
        $(".checkbox__box").click();
        $(".button__text").click();
        $(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void submitWithFalseCity() {
        $("[placeholder='Город']").setValue("Вена");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(dataGenerator.forwardDate(3));
        $("[name=name]").setValue(dataGenerator.createName());
        $("[name=phone]").setValue(dataGenerator.createCellPhone());
        $(".checkbox__box").click();
        $(".button__text").click();
        $(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void submitWithoutName() {
        $("[placeholder='Город']").setValue(dataGenerator.createCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(dataGenerator.forwardDate(3));
        $("[name=phone]").setValue(dataGenerator.createCellPhone());
        $(".checkbox__box").click();
        $(".button__text").click();
        $(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void submitWithNameInLatin() {
        $("[placeholder='Город']").setValue(dataGenerator.createCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(dataGenerator.forwardDate(3));
        $("[name=name]").setValue(dataGenerator.createFalseName());
        $("[name=phone]").setValue(dataGenerator.createCellPhone());
        $(".checkbox__box").click();
        $(".button__text").click();
        $(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void submitWithoutPhone() {
        $("[placeholder='Город']").setValue(dataGenerator.createCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(dataGenerator.forwardDate(3));
        $("[name=name]").setValue(dataGenerator.createName());
        $(".checkbox__box").click();
        $(".button__text").click();
        $(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void submitWithoutCheckbox() {
        $("[placeholder='Город']").setValue(dataGenerator.createCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(dataGenerator.forwardDate(3));
        $("[name=name]").setValue(dataGenerator.createName());
        $("[name=phone]").setValue(dataGenerator.createCellPhone());
        $(".button__text").click();
        $(".input_invalid").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
