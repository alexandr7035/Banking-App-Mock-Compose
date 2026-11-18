from selenium.webdriver.support.ui import WebDriverWait


class HistoryPage:
    FILTROS = ["All", "Send", "Request", "Top up"]

    def __init__(self, driver):
        self.driver = driver

    def validar_filtros_visiveis(self):
        WebDriverWait(self.driver, 20).until(
            lambda d: all(
                f'text="{filtro}"' in d.page_source
                for filtro in self.FILTROS
            )
        )
        return self
