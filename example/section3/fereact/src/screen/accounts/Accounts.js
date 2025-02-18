import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axiosAuthInstance from "../../api/API";

export default function Accounts() {
  let navigate = useNavigate();
  let [accounts, setAccounts] = useState([]);

  useEffect(() => {
    fetchData();
  }, []);

  let fetchData = () => {
    axiosAuthInstance.get("/user/accounts")
      .then(function (response) {
        setAccounts(response.data);
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  let deleteAccount = (id) => {
    axiosAuthInstance.delete('/user/account/' + id)
      .then(function () {
        console.log("ok");
        fetchData();
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  let navigateEditAccount = (account) => {
    navigate("/account/edit", { state: { account } });
  };

  return <>
    <div className="col-12 mt-4">
      <Link to="/account/add" className="btn btn-primary">Create New</Link>
    </div>
    <div className="col-12 mt-4 table-responsive">
      <table className="table table-bordered table-hover align-middle">
        <thead>
          <tr>
            <th >#</th>
            <th >Name</th>
            <th >Username</th>
            <th >Email</th>
            <th >Roles</th>
            <th >Options</th>
          </tr>
        </thead>
        <tbody>
          {accounts.map(account => (
            <tr key={account.id}>
              <th >{account.id}</th>
              <td>{account.name}</td>
              <td>{account.username}</td>
              <td>{account.email}</td>
              <td>{account.roles.toString()}</td>
              <td>
                <button onClick={() => navigateEditAccount(account)} type="button" className="btn btn-outline-primary">Edit</button>
                <button onClick={() => deleteAccount(account.id)} type="button" className="btn btn-outline-danger m-1">Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  </>;
}