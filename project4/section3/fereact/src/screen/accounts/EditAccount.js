import { useState } from "react";
import { useLocation, useNavigate } from "react-router";
import axiosAuthInstance from "../../api/API";

export default function EditAccount() {
  let navigate = useNavigate();
  const location = useLocation();

  const [account, setAccount] = useState(location.state.account);
  const [error, setError] = useState();

  let setParam = (event) => {
    setAccount({ ...account, [event.target.name]: event.target.value });
  };

  //checkbox
  let setRoles = (event) => {
    const value = event.target.value;

    if (event.target.checked) {
      setAccount({ ...account, roles: [...account.roles, value] });
    } else {
      let roles = account.roles.filter(item => {
        return item !== value;
      });

      setAccount({ ...account, roles });
    }
  };

  let saveUser = async () => {
    axiosAuthInstance.put("/user/account", account)
      .then(function () {
        navigate(-1);
      })
      .catch(function (error) {
        setError(error.message);
      });
  };

  return <>
    <div className="col-12 mt-4">
      <button onClick={() => navigate(-1)} type="button" className="btn btn-success">List Accounts</button>
    </div >
    <div className="col-12 mt-4 table-responsive">
      <form>
        <div className="mb-3">
          <label className="form-label">Name</label>
          <input type="text" className="form-control" name="name" value={account.name} onChange={setParam} />
        </div>
        <div className="mb-3">
          <label className="form-label">Username</label>
          <input type="text" className="form-control" name="username" value={account.username} onChange={setParam} />
        </div>
        <div className="mb-3">
          <label className="form-label">Email address</label>
          <input type="email" className="form-control" value={account.email} name="email" onChange={setParam} />
        </div>
        <div className="mb-3">
          <label className="form-label">Roles</label>
          <div className="form-check">
            <label className="form-check-label" >
              <input className="form-check-input" type="checkbox" defaultChecked={account.roles.indexOf("ROLE_ADMIN") !== -1} value="ROLE_ADMIN" onChange={setRoles} />
              ROLE ADMIN
            </label>
          </div>
          <div className="form-check">
            <label className="form-check-label" >
              <input className="form-check-input" type="checkbox" defaultChecked={account.roles.indexOf("ROLE_USER") !== -1} value="ROLE_USER" onChange={setRoles} />
              ROLE USER
            </label>
          </div>
        </div>
        <div className="mb-3">
          <p className="text-danger">{error}</p>
          <button onClick={saveUser} type="button" className="btn btn-primary">Save</button>
        </div>
      </form>
    </div>
  </>;
}