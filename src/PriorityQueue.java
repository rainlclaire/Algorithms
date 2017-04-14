import java.util.ArrayList;

/**
 *
 *
 *
 * ****** Usage ******
 * 1. Create new PriorityQueue by calling
 *           new PriorityQueue()
 * 2. Add new employee to PriorityQueue:
 *           insert(employee);
 * 3. Extract the max influence value by calling: (Note: this
 * will cause the relative QueueElement removed from the queue as well)
 *           heap_extract_max();
 */

public class PriorityQueue {
    int heap_size;
    ArrayList<QueueElement> elements;

    public PriorityQueue(){
        /*start with size */
        heap_size = 0;
        /*start with empty array list */
        elements = new ArrayList<QueueElement>();
    }


    /**
     * Inner class, which mapping the leaf employee and
     * its total-influence
     */
    class QueueElement {
        Employee employee;
        int total_influence;

        QueueElement(Employee e, int total_influence) {
            this.employee = e;
            this.total_influence = total_influence;
        }

        /*update_element when we create object*/
        void update_element(){
            total_influence = employee.total_influence();
        }
    }

    public void update_queue() {
        /* update element's total_influence value */

        for (int i = heap_size; i >= 1; i--) {
            elements.get(i-1).update_element();
            heap_decrease_key(i, elements.get(i - 1).total_influence);
        }
    }


    /*heap decrease key method by compare the element is given index, and key*/
    public void heap_decrease_key(int index, int key) {

        if (key > get_element(index))
            System.err.println("new key is bigger than current key");
        set_element(index, key);
        int l, r, largest = 0;
        while (index <= heap_size / 2) {
            l = left(index);
            r = right(index);
            largest = index;

            if (l <= heap_size && key < get_element(l))
                largest = l;
            if (r <= heap_size && get_element(largest) < get_element(r))
                largest = r;

            if (largest != index) {
                swap(index, largest);
                index = largest;
            } else {
                break;
            }
        }
    }


    Employee find_employee_with_id(int id){
        /* null is returned if employee not found */
        Employee employee = null;
        for (QueueElement e: elements) {
            if (e.employee.id() == id) {
                employee = e.employee;
            }
        }
        return employee;
    }

    public int get_element(int index){
        return elements.get(index - 1).total_influence;
    }

    public void set_element(int index, int value){
        QueueElement tmp = elements.get(index - 1);
        tmp.total_influence = value;
    }

    public int heap_extract_max(){
        /*
         * 1. get the max
         * 2. substitute first element with the last one
         * 3. heap_size - 1
         * 4. update queue to subtract parsed influence
         * 5. return max
         */
        if (heap_size < 1)
            System.err.println("heap underflow");
        int max = this.get_element(1);
        elements.get(0).employee.set_employee_and_bosses_as_parsed();

        this.swap(1, heap_size);
        heap_size--;
        update_queue();
        return max;
    }


    void heap_increase_key(int index, int key){
        if (key < get_element(index))
            System.err.println("new key is smaller than current key");
        set_element(index, key);
        while ( index > 1 &&
                get_element(parent(index)) < get_element(index)){
            this.swap(index, parent(index));
            index = parent(index);
        }
    }

    public void max_heap_insert(QueueElement element){
        heap_size++;
        /*make the heap start with 1*/
        QueueElement tmp = new QueueElement(element.employee, -1);
        elements.add(tmp);
        /*then, add the real emplpoyee*/
        heap_increase_key(heap_size, element.employee.total_influence());
    }

    public void insert(Employee employee){
        QueueElement e = new QueueElement(employee, employee.total_influence());
        this.max_heap_insert(e);
    }


    public void max_heapify(int i){
        int l = left(i);
        int r = right(i);
        int largest;

        if (l <= heap_size &&
                this.get_element(l) > this.get_element(i))
            largest = l;
        else largest = i;
        if (r <= heap_size &&
                this.get_element(r) > this.get_element(largest))
            largest = r;
        if (largest != i){
            this.swap(i, largest);
            max_heapify(largest);
        }
    }


    public void swap(int i1, int i2){
        QueueElement tmp = elements.get(i1 - 1);
        elements.set(i1 - 1, elements.get(i2 - 1));
        elements.set(i2 - 1, tmp);
    }

    public int parent(int i){
        return i/2;
    }

    public int left(int i){
        return 2*i;
    }

    public int right (int i){
        return 2*i + 1;
    }


}
