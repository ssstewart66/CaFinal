package com.example.c23team2.Controllers;

import com.example.c23team2.Interfacemethods.AdminService;
import com.example.c23team2.Interfacemethods.LeaveTypeService;
import com.example.c23team2.Models.LeaveType;
import com.example.c23team2.Models.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("Admin")
public class   AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private LeaveTypeService leaveTypeService;

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", adminService.findAllUsers());
        return "/user-list";
    }


    @RequestMapping("/users/create")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        return "/user-create";
    }

    @RequestMapping("/users/save")
    public String saveUser(@ModelAttribute("user") @Valid User inuser , BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.toString()));
            return "/user-create";
        }

        if(inuser.getDepartment()==0){
            Optional<LeaveType> AnnualleaveTypeO = leaveTypeService.findLeaveTypeById(1);
            Optional<LeaveType> MedicalleaveTypeO = leaveTypeService.findLeaveTypeById(3);
            LeaveType AnnualleaveType = AnnualleaveTypeO.get();
            LeaveType MedicalleaveType = MedicalleaveTypeO.get();
            inuser.setAnnual_leave_entitlement(AnnualleaveType.getMaxdays());
            inuser.setMedical_leave_entitlement(MedicalleaveType.getMaxdays());
        }else{
            Optional<LeaveType> AnnualleaveTypeO = leaveTypeService.findLeaveTypeById(2);
            Optional<LeaveType> MedicalleaveTypeO = leaveTypeService.findLeaveTypeById(3);
            LeaveType AnnualleaveType = AnnualleaveTypeO.get();
            LeaveType MedicalleaveType = MedicalleaveTypeO.get();
            inuser.setAnnual_leave_entitlement(AnnualleaveType.getMaxdays());
            inuser.setMedical_leave_entitlement(MedicalleaveType.getMaxdays());
        }

        inuser.setAnnual_leave_entitlement_last(inuser.getAnnual_leave_entitlement());
        inuser.setMedical_leave_entitlement_last(inuser.getMedical_leave_entitlement());

        adminService.updateUser(inuser);
        return "redirect:/Admin/users";
    }

    @RequestMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        if (adminService.findUserById(id).isPresent()) {
            User usertoDelete = adminService.findUserById(id).get();
            adminService.deleteUser(usertoDelete);
        }
        else{
            return "redirect:/";
        }
        return "redirect:/Admin/users";
    }

    @GetMapping("/users/update/{id}")
    public String updateUser(@PathVariable int id, Model model) {
        Optional<User> user = adminService.findUserById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "user-create";
        }else {
            return "redirect:/Admin/users";
        }
    }

    @GetMapping("/leavetypes")
    public String listLeaveTypes(Model model) {
        model.addAttribute("leavetypes", leaveTypeService.findAllLeaveTypes());
        return "leaveTypes-manage";
    }


    @RequestMapping("/leavetypes/create")
    public String createLeaveType(Model model) {
        model.addAttribute("leavetype", new LeaveType());
        return "leaveTypes-create";
    }

    @RequestMapping("/leavetypes/save")
    public String saveLeaveType(@ModelAttribute("leavetype") @Valid LeaveType leaveType, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.toString()));
            return "leaveTypes-create";
        }
        leaveTypeService.createLeaveType(leaveType);
        return "redirect:/Admin/leavetypes";
    }

    @RequestMapping("/leavetypes/delete/{id}")
    public String deleteLeaveType(@PathVariable int id) {
        Optional<LeaveType> leaveType = leaveTypeService.findLeaveTypeById(id);
        if (leaveType.isPresent()) {
            leaveTypeService.deleteLeaveType(leaveType.get());
        }
        return "redirect:/Admin/leavetypes";
    }

    @GetMapping("/leavetypes/update/{id}")
    public String updateLeaveType(@PathVariable int id, Model model) {
        Optional<LeaveType> leaveType = leaveTypeService.findLeaveTypeById(id);
        if (leaveType.isPresent()) {
            model.addAttribute("leavetype", leaveType.get());
            return "leaveTypes-create";
        } else {
            return "redirect:/Admin/leavetypes";
        }
    }

    @GetMapping("/leaveentitlements")
    public String listLeaveEntitlements(Model model) {
        model.addAttribute("users", adminService.findAllUsers());
        return "leaveEntitlement-manage";
    }

    @RequestMapping("/leaveentitlements/save")
    public String saveLeaveEntitlement(@ModelAttribute("usertoupdate") @Valid User inuser, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.toString()));
            return "leaveEntitlement-update";
        }
        Optional<User> originalUser = adminService.findUserById(inuser.getId());
        if (originalUser.isPresent()) {
            User existingUser = originalUser.get();

            inuser.setUsername(existingUser.getUsername());
            inuser.setPassword(existingUser.getPassword());
            inuser.setRole(existingUser.getRole());
            inuser.setAccount(existingUser.getAccount());
            inuser.setEmail(existingUser.getEmail());
            inuser.setDepartment(existingUser.getDepartment());
        }
        adminService.updateUser(inuser);
        return "redirect:/Admin/leaveentitlements";
    }

    @GetMapping("/leaveentitlements/update/{id}")
    public String updateLeaveEntitlement(@PathVariable int id, Model model) {
        Optional<User> usertoupdate = adminService.findUserById(id);
        if (usertoupdate.isPresent()) {
            model.addAttribute("usertoupdate", usertoupdate.get());
            return "leaveEntitlement-update";
        } else {
            return "redirect:/Admin/leaveentitlements";
        }
    }
}