from appium.webdriver.common.appiumby import AppiumBy
from selenium.webdriver.support.ui import WebDriverWait


class DashboardPage:
    HOME = 'new UiSelector().text("Home")'
    HISTORY = 'new UiSelector().text("History")'

    def __init__(self, driver):
        self.driver = driver

    def abrir_home(self):
        self.driver.find_element(
            AppiumBy.ANDROID_UIAUTOMATOR, self.HOME
        ).click()
        return self

    def validar_saldo_visivel(self):
        WebDriverWait(self.driver, 20).until(
            lambda d: "My Balance" in d.page_source
        )
        return self

    def abrir_historico(self):
        self.driver.find_element(
            AppiumBy.ANDROID_UIAUTOMATOR, self.HISTORY
        ).click()
        return self
