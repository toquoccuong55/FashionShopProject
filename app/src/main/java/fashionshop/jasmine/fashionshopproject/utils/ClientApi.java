package fashionshop.jasmine.fashionshopproject.utils;

import fashionshop.jasmine.fashionshopproject.fashionShopRepository.fashionService;

public class ClientApi extends BaseApi {
    public fashionService fashionService() {
        return this.getService(fashionService.class, ConfigApi.BASE_URL);
    }
}
