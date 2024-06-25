import React, { useState, useEffect } from 'react';
import axios from 'axios';

const SettingForm = () => {
    const [leaveTypes, setLeaveTypes] = useState([]);
    const [form, setForm] = useState({
        leaveType: '',
        start_date: '',
        end_date: '',
        reason: '',
        work_dissemination: '',
        contact_details: ''
    });
    const [errors, setErrors] = useState({});

    useEffect(() => {
        // Fetch leave types from the server
        axios.get('http://localhost:8080/leave-types')
            .then(response => {
                setLeaveTypes(response.data);
            })
            .catch(error => {
                console.error('There was an error fetching the leave types!', error);
            });
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm({ ...form, [name]: value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        // Submit form data to the server
        axios.post('http://localhost:8080/staff/apply-leave', form)
            .then(response => {
                console.log('Leave application submitted successfully:', response.data);
                // Clear the form and errors after successful submission
                setForm({
                    leaveType: '',
                    start_date: '',
                    end_date: '',
                    reason: '',
                    work_dissemination: '',
                    contact_details: ''
                });
                setErrors({});
            })
            .catch(error => {
                console.error('There was an error submitting the leave application!', error);
                if (error.response && error.response.data) {
                    setErrors(error.response.data);
                }
            });
    };

    return (
        <div className="container">
            <h1 className="mt-5">Apply Leave</h1>
            <form onSubmit={handleSubmit} className="mt-3">
                <div className="form-group">
                    <label htmlFor="leaveType">Leave Type:</label>
                    <select
                        id="leaveType"
                        name="leaveType"
                        value={form.leaveType}
                        onChange={handleChange}
                        className="form-control"
                    >
                        <option value="">Select Leave Type</option>
                        {leaveTypes.map(leaveType => (
                            <option key={leaveType.id} value={leaveType.id}>
                                {leaveType.name}
                            </option>
                        ))}
                    </select>
                    {errors.leaveType && <div className="text-danger">{errors.leaveType}</div>}
                </div>
                <div className="form-group">
                    <label htmlFor="start_date">Start Date:</label>
                    <input
                        type="date"
                        id="start_date"
                        name="start_date"
                        value={form.start_date}
                        onChange={handleChange}
                        className="form-control"
                    />
                    {errors.start_date && <div className="text-danger">{errors.start_date}</div>}
                </div>
                <div className="form-group">
                    <label htmlFor="end_date">End Date:</label>
                    <input
                        type="date"
                        id="end_date"
                        name="end_date"
                        value={form.end_date}
                        onChange={handleChange}
                        className="form-control"
                    />
                    {errors.end_date && <div className="text-danger">{errors.end_date}</div>}
                </div>
                <div className="form-group">
                    <label htmlFor="reason">Reason:</label>
                    <textarea
                        id="reason"
                        name="reason"
                        value={form.reason}
                        onChange={handleChange}
                        className="form-control"
                    ></textarea>
                    {errors.reason && <div className="text-danger">{errors.reason}</div>}
                </div>
                <div className="form-group">
                    <label htmlFor="work_dissemination">Work Dissemination:</label>
                    <textarea
                        id="work_dissemination"
                        name="work_dissemination"
                        value={form.work_dissemination}
                        onChange={handleChange}
                        className="form-control"
                    ></textarea>
                    {errors.work_dissemination && <div className="text-danger">{errors.work_dissemination}</div>}
                </div>
                <div className="form-group">
                    <label htmlFor="contact_details">Contact Details (if on an overseas trip):</label>
                    <textarea
                        id="contact_details"
                        name="contact_details"
                        value={form.contact_details}
                        onChange={handleChange}
                        className="form-control"
                    ></textarea>
                    {errors.contact_details && <div className="text-danger">{errors.contact_details}</div>}
                </div>
                <button type="submit" className="btn btn-primary">Submit</button>
            </form>
        </div>
    );
};

export default SettingForm;
