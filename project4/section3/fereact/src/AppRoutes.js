import Accounts from "./screen/accounts/Accounts";
import AddAccount from "./screen/accounts/AddAccount";
import EditAccount from "./screen/accounts/EditAccount";
import ClientRegister from "./screen/client/ClientRegister";
import Statistic from "./screen/statistic/Statistic";

export const routes = [
  {
    path: 'accounts',
    component: <Accounts />,
    name: "Accounts",
  },
  {
    path: 'account/add',
    component: <AddAccount />,
    name: "Add Account",
  },
  {
    path: 'account/edit',
    component: <EditAccount />,
    name: "Edit Account",
  },
  {
    path: 'statistics',
    component: <Statistic />,
    name: "Statistics",
  },
  {
    path: 'clients',
    component: <ClientRegister />,
    name: "Clients Register",
  },

];