import pytest
from appium import webdriver
from appium.options.android import UiAutomator2Options

from qa_mobile.pages.dashboard_page import DashboardPage
from qa_mobile.pages.history_page import HistoryPage

APP_PACKAGE = "by.alexandr7035.banking.debug"


@pytest.fixture
def driver():
    options = UiAutomator2Options()
    options.platform_name = "Android"
    options.automation_name = "UiAutomator2"
    options.device_name = "emulator-5554"
    options.app_package = APP_PACKAGE
    options.no_reset = True

    session = webdriver.Remote(
        "http://127.0.0.1:4723",
        options=options
    )

    yield session
    session.quit()


def test_dashboard_exibe_saldo(driver):
    dashboard = DashboardPage(driver)

    dashboard.abrir_home()
    dashboard.validar_saldo_visivel()

    assert driver.current_package == APP_PACKAGE


def test_navegacao_para_historico(driver):
    dashboard = DashboardPage(driver)
    historico = HistoryPage(driver)

    dashboard.abrir_home()
    dashboard.abrir_historico()
    historico.validar_filtros_visiveis()

    assert driver.current_package == APP_PACKAGE
