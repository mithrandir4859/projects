package carrental.web.controllers.actions.impl.empty;

import carrental.domain.UserStatus;
import carrental.web.controllers.actions.GenericAction;
import carrental.web.controllers.actions.annotations.Access;
import carrental.web.controllers.actions.annotations.Mapping;

@Access(UserStatus.ADMIN)
@Mapping(view = "/selectOrders.jsp")
public class SelectOrders extends GenericAction {}
