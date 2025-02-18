import { useEffect, useState } from "react";
import axiosAuthInstance from "../../api/API";

export default function Statistic() {

  let [statistics, setStatistics] = useState([]);

  useEffect(() => {
    fetchData();
  }, []);

  let fetchData = () => {
    axiosAuthInstance.get("/report/statistics")
      .then(function (response) {
        setStatistics(response.data);
        console.log(response.data);
      }).catch(function (error) {
        console.log(error);
      });
  };

  return <>
    <div className="col-12 mt-4 table-responsive">
      <table className="table table-bordered table-hover align-middle">
        <thead>
          <tr>
            <th >#</th>
            <th >Message</th>
            <th >Time</th>
          </tr>
        </thead>
        <tbody>
          {statistics.map(statistic => (
            <tr key={statistic.id}>
              <th >{statistic.id}</th>
              <td>{statistic.message}</td>
              <td>{statistic.createdDate}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  </>;
}