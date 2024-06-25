import React, { useState } from 'react';
import axios from 'axios';
import { CSVLink } from 'react-csv';

const ReportGenerator = () => {
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [reportData, setReportData] = useState([]);
    const [error, setError] = useState(null);

    const handleGenerateReport = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/reports?startDate=${startDate}&endDate=${endDate}`);
            setReportData(response.data);
            setError(null);
        } catch (error) {
            setError('Error fetching leave report');
            console.error('Error fetching leave report:', error);
        }
    };

    const headers = [
        { label: "ID", key: "id" },
        { label: "User ID", key: "userId" },
        { label: "Leave Type", key: "leaveType.name" },
        { label: "Start Date", key: "start_date" },
        { label: "End Date", key: "end_date" },
        { label: "Status", key: "status" },
        { label: "Reason", key: "reason" },
        { label: "Work Dissemination", key: "work_dissemination" },
        { label: "Contact Details", key: "contact_details" },
        { label: "Created At", key: "created_at" },
        { label: "Updated At", key: "updated_at" }
    ];

    return (
        <div>
            <h1>Generate Report</h1>
            <div>
                <label>Start Date: </label>
                <input type="date" value={startDate} onChange={(e) => setStartDate(e.target.value)} />
            </div>
            <div>
                <label>End Date: </label>
                <input type="date" value={endDate} onChange={(e) => setEndDate(e.target.value)} />
            </div>
            <button onClick={handleGenerateReport}>Generate Report</button>
            {error && <p>{error}</p>}
            {reportData.length > 0 && (
                <div>
                    <h2>Report Data</h2>
                    <table>
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>User ID</th>
                            <th>Leave Type</th>
                            <th>Start Date</th>
                            <th>End Date</th>
                            <th>Status</th>
                            <th>Reason</th>
                            <th>Work Dissemination</th>
                            <th>Contact Details</th>
                            <th>Created At</th>
                            <th>Updated At</th>
                        </tr>
                        </thead>
                        <tbody>
                        {reportData.map((application) => (
                            <tr key={application.id}>
                                <td>{application.id}</td>
                                <td>{application.userId}</td>
                                <td>{application.leaveType.name}</td>
                                <td>{application.start_date}</td>
                                <td>{application.end_date}</td>
                                <td>{application.status}</td>
                                <td>{application.reason}</td>
                                <td>{application.work_dissemination}</td>
                                <td>{application.contact_details}</td>
                                <td>{new Date(application.created_at).toLocaleString()}</td>
                                <td>{new Date(application.updated_at).toLocaleString()}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                    <CSVLink data={reportData} headers={headers} filename={`leave_report_${startDate}_to_${endDate}.csv`}>
                        <button>Download CSV</button>
                    </CSVLink>
                </div>
            )}
        </div>
    );
};

export default ReportGenerator;
