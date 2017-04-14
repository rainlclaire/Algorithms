/**
 *
 *
 *
 * ***** Usage ******
 * Note: you don't worry about updating the boss of the employee, since it
 *      is automatically updated when you create this employee with the default constructor.
 * • int id()               : to get employee id.
 * • int influence()        : to get the influence value of this employee himself.
 * • int total_influence()  : to get the total max effect by influencing this employee.
 * • boolean has_sub()      : does this employee has subordinates?
 */
public class Employee {
    public int _id;
    public int _influence;
    public Employee boss;

    public boolean is_parsed; /* has this employee been walked */
    public boolean has_sub;    /* whether this employee has subordinates */

    public Employee(int id, int influence, Employee boss) {
        this._id = id;
        this._influence = influence;
        /*if this employee is not ceo*/
        if (id != 0){
            this.boss = boss;
            boss.has_sub = true;
        }

        this.is_parsed = false;
        this.has_sub = false;
    }


    public int id(){
        return _id;
    }

    public int influence() {
        return _influence;
    }

    /*compute the total_influence from the employee to ceo*/
    public int total_influence () {
        int result = 0;

        Employee current_employee = this;
        while (current_employee.id() != 0) {
            if (!current_employee.is_parsed)
                result += current_employee.influence();

            current_employee = current_employee.boss;
        }

        return result;
    }

    /**is the employee has subordinate*/
    public boolean has_sub() {
        return has_sub;
    }

    /*set the employee and its bosses parsed boolean to true*/
    public void set_employee_and_bosses_as_parsed(){
        Employee current_employee = this;
        while (current_employee.id() != 0) {
            current_employee.is_parsed = true;

            current_employee = current_employee.boss;
        }
    }
}
