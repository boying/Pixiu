package bean;

/**
 * Created by boying on 2017/9/28.
 */
public class V {
    private Long id;

    public V() {
    }

    public V(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        if(id != null){
            return id.hashCode();
        }

        return this.getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        V v = (V)obj;
        if(this.getId() == null && v.getId() != null){
            return false;
        }

        if(this.getId() != null && v.getId() == null){
            return false;
        }

        if(this.getId() == null && v.getId() == null){
            return true;
        }

        return v.getId().equals(this.getId());
    }
}
