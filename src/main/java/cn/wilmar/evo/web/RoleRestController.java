package cn.wilmar.evo.web;

//import cn.yinguowei.myapp.model.Role;
//import cn.yinguowei.myapp.model.RoleRepository;

import cn.wilmar.evo.model.Role;
import cn.wilmar.evo.repository.RoleRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

/**
 * @author yinguowei 2018/2/19.
 * <p>
 * To Create a resource : HTTP POST should be used
 * To Retrieve a resource : HTTP GET should be used
 * To Update a resource : HTTP PUT should be used
 * To Delete a resource : HTTP DELETE should be used
 * GET request to /api/role/ returns a list of roles
 * GET request to /api/role/1 returns the role with ID 1
 * POST request to /api/role/ with a role object as JSON creates a new role
 * PUT request to /api/role/3 with a role object as JSON updates the role with ID 3
 * DELETE request to /api/role/4 deletes the role with ID 4
 * DELETE request to /api/role/ deletes all the roles
 */
@RestController
@RequestMapping("/api")
public class RoleRestController {

    //Service which will do all data retrieval/manipulation work
    private final RoleRepository roleRepository;

    public RoleRestController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    //-------------------Retrieve All Roles 获取所有Role对象--------------------------------------------------------

    @RequestMapping(value = "/role/", method = RequestMethod.GET)
    public ResponseEntity<List<Role>> listAllRoles() {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            //You many decide to return HttpStatus.NOT_FOUND
            return new ResponseEntity<List<Role>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Role>>(roles, HttpStatus.OK);
    }


    //-------------------Retrieve Single Role--------------------------------------------------------

    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Role> getRole(@PathVariable("id") long id) {
        System.out.println("Fetching Role with id " + id);
        Optional<Role> role = roleRepository.findById(id);
        if (!role.isPresent()) {
            System.out.println("Role with id " + id + " not found");
            return new ResponseEntity<Role>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Role>(role.get(), HttpStatus.OK);
    }


    //-------------------Create a Role--------------------------------------------------------

    @RequestMapping(value = "/role/", method = RequestMethod.POST)
    public ResponseEntity<Void> createRole(@RequestBody Role role, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Role " + role.getName());

        if (roleRepository.findOneByKey(role.getKey()) != null || roleRepository.findOneByKey(role.getName()) != null) {
            System.out.println("A Role with name \"" + role.getName() + "\" or key \"" + role.getKey() + "\" already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        roleRepository.save(role);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/role/{id}").buildAndExpand(role.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }


    //------------------- Update a Role --------------------------------------------------------

    @RequestMapping(value = "/role/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Role> updateRole(@PathVariable("id") long id, @RequestBody Role role) {
        System.out.println("Updating Role " + id);

        Optional<Role> currentRole = roleRepository.findById(id);

        if (!currentRole.isPresent()) {
            System.out.println("Role with id " + id + " not found");
            return new ResponseEntity<Role>(HttpStatus.NOT_FOUND);
        }

        Role r = currentRole.get();
        r.setName(role.getName());
        r.setKey(role.getKey());

        roleRepository.save(r);
        return new ResponseEntity<Role>(r, HttpStatus.OK);

    }

    //------------------- Delete a Role --------------------------------------------------------

    @RequestMapping(value = "/role/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Role> deleteRole(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting Role with id " + id);

        Optional<Role> role = roleRepository.findById(id);

        if (!role.isPresent()) {
            System.out.println("Role with id " + id + " not found");
            return new ResponseEntity<Role>(HttpStatus.NOT_FOUND);
        }

        roleRepository.delete(role.get());
        return new ResponseEntity<Role>(HttpStatus.NO_CONTENT);
    }


    //------------------- Delete All Roles --------------------------------------------------------

    @RequestMapping(value = "/role/", method = RequestMethod.DELETE)
    public ResponseEntity<Role> deleteAllRoles() {
        System.out.println("Deleting All Roles");

        roleRepository.deleteAll();
        return new ResponseEntity<Role>(HttpStatus.NO_CONTENT);
    }

}

