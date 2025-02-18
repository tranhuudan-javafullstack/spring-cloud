import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axiosAuthInstance from "../../api/API";

export default function AddAccount() {
  let navigate = useNavigate();

  const [account, setAccount] = useState({
    name: '',
    password: '',
    username: '',
    email: '',
    roles: []
  });
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
    axiosAuthInstance.post("/user/account", account)
      .then(function (response) {
        console.log(response.data);
        navigate(-1);
      })
      .catch(function (error) {
        setError(error.message);
      });
  };

  return <>
    <div className="col-12 mt-4">
      <button onClick={() => navigate(-1)} type="button" className="btn btn-success">List Accounts</button>
    </div>
    <div className="col-12 mt-4 table-responsive">
      <form>
        <div className="mb-3">
          <label className="form-label">Name</label>
          <input type="text" className="form-control" name="name" onChange={setParam} />
        </div>
        <div className="mb-3">
          <label className="form-label">Username</label>
          <input type="text" className="form-control" name="username" onChange={setParam} />
        </div>
        <div className="mb-3">
          <label className="form-label">Password</label>
          <input type="password" className="form-control" name="password" onChange={setParam} />
        </div>
        <div className="mb-3">
          <label className="form-label">Email address</label>
          <input type="email" className="form-control" placeholder="name@example.com" name="email" onChange={setParam} />
        </div>
        <div className="mb-3">
          <label className="form-label">Roles</label>
          <div className="form-check">
            <label className="form-check-label" >
              <input className="form-check-input" type="checkbox" value="ROLE_ADMIN" onChange={setRoles} />
              ROLE ADMIN
            </label>
          </div>
          <div className="form-check">
            <label className="form-check-label" >
              <input className="form-check-input" type="checkbox" value="ROLE_USER" onChange={setRoles} />
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